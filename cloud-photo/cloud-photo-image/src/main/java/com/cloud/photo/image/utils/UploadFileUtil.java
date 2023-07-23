package com.cloud.photo.image.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class UploadFileUtil {
    public static Boolean uploadSinglePart(File file, String url){

        FileInputStream fileInputStream = null;
        InputStream inputStream = null;
        try {

            //设置请求头
            HttpPut httpPut = new HttpPut(url);;
            httpPut.setConfig(getRequestConfig());
            httpPut.setHeader("Content-Type", "application/octet-stream");


            //发送请求，失败时重试两次
            int responseCode;
            int retryCount = 2;
            CloseableHttpResponse httpResponse = null;
            CloseableHttpClient closeableHttpClient = null;
            while (retryCount > 0) {
                //将分片内容设置进请求体
                fileInputStream = new FileInputStream(file);
                InputStreamEntity reqEntity = new InputStreamEntity(fileInputStream, file.length());
                httpPut.setEntity(reqEntity);

                //创建客户端执行请求
                closeableHttpClient = HttpClients.createDefault();
                httpResponse = closeableHttpClient.execute(httpPut);
                responseCode = httpResponse.getStatusLine().getStatusCode();
                if (responseCode == 200) {
                    break;
                } else {
                    retryCount--;
                }
                closeableHttpClient.close();
                httpResponse.close();
            }
            //如果重试两次后响应仍然不为200，则记录失败状态
            responseCode = httpResponse.getStatusLine().getStatusCode();
            if (retryCount == 2 && responseCode != 200) {
                return false;
            }
        } catch (IOException e) {
            log.error("upload error!" , e);
            return false;
        } finally {
            try {

                if (fileInputStream != null){
                    fileInputStream.close();
                }

                if (inputStream != null){
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private static RequestConfig getRequestConfig(){
        return RequestConfig.custom()
                //从连接池中获取连接的超时时间
                .setConnectionRequestTimeout(3000000)
                //与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
                .setConnectTimeout(3000000)
                //socket读数据超时时间：从服务器获取响应数据的超时时间
                .setSocketTimeout(3000000)
                .build();
    }
}
