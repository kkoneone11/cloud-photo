package com.cloud.photo.image.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;

import java.io.*;

@Slf4j
public class DownloadFileUtil {



	public static Boolean downloadFile(String url, String fileName) {
		Boolean result = false;

		log.info("downloadFile() - fileName=" + fileName + ", url=" + url);

		BufferedOutputStream bos = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(new File(fileName));
			bos = new BufferedOutputStream(fileOutputStream);
		} catch (Exception e) {
			log.warn("downloadFile() - fileName=" + fileName + ", url=" + url + ", FileOutputStream error. ");
			return result;
		}
		HttpClient client = getHttpClient();

		HttpGet get = null;
		InputStream is = null;
		int k = 0;

		while (k < 3) {
			log.info("test - k = "+k);
			try {
				
				get = new HttpGet(url);
				
				HttpResponse resp = client.execute(get);

				int status = resp.getStatusLine().getStatusCode();

				if (status == HttpStatus.SC_OK) {
					is = resp.getEntity().getContent();

					byte[] k8 = new byte[8 * 1024];

					int i = 0;

					while ((i = is.read(k8)) != -1) {
						bos.write(k8, 0, i);
					}
					bos.flush();
					result = true;
					break;
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					if(is != null){
						is.close();
					}
					if (get != null) {
						get.abort();
					}
				} catch (Exception e) {
				}
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}
			k++;
		}

		log.info("downloadFile() - fileName=" + fileName + ", url=" + url + ", result=" + result);

		return result;

	}

	/**
	 *
	 * @Title: getHttpClient
	 * @Description: 获取Httpclient
	 * @return    设定文件
	 * @return DefaultHttpClient    返回类型
	 * @throws
	 */
	public static DefaultHttpClient getHttpClient( ) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,"utf-8");

		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 30 * 1000);
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 30 * 1000);

		DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3, true);// 重试3次

		httpclient.setHttpRequestRetryHandler(retryHandler);

		return httpclient;
	}
}
