package com.cloud.photo.api.utils;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.buf.HexUtils;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class S3Util {




    /**
     * 华为obs存储
     */
  /*  static String accessKey = "RCGYTPDXWMBPNZZUUQPU";
    static String secretKey = "nXaANCyR49YsynCxe6nmydRPSrSHWlW2Kq45ab1F";
    static String serviceEndpoint = "http://obs.cn-south-1.myhuaweicloud.com";
    static String bucketName = "cloud-photo";*/

    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";
    private static String bucketName = "cloud-photo";
    private static String serviceEndpoint = "http://127.0.0.1:9000";


    private static String containerId = "10001";

    /**
     * 获取上传地址
     * @param suffixName
     * @param fileMd5
     * @return
     */
    public static String getPutUploadUrl(String suffixName,String fileMd5) {

        //链接过期时间
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);
        String objectId = UUID.randomUUID().toString().replaceAll("-","");
        String base64Md5 = "";
        if(StringUtils.isNotBlank(suffixName)){
            objectId = objectId +"." + suffixName;
        }
        //建立S3客户端，获取上传地址
        AmazonS3 s3Client = getAmazonS3Client();
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectId)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);
        if(StringUtils.isNotBlank(fileMd5)){
            base64Md5= Base64.encode(HexUtils.fromHexString(fileMd5));
            generatePresignedUrlRequest = generatePresignedUrlRequest.withContentMd5(base64Md5);
        }
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("objectId",objectId);
        jsonObject.put("url",url);
        jsonObject.put("containerId",containerId);
        jsonObject.put("base64Md5",base64Md5);
        return jsonObject.toJSONString();
    }

    /**
     * 获取文件资源池信息
     * @param objectId
     * @return
     */
    public static S3ObjectSummary getObjectInfo(String objectId){
        AmazonS3 s3Client = getAmazonS3Client();

        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(objectId);

        ListObjectsV2Result listing = s3Client.listObjectsV2(listObjectsRequest);
        List<S3ObjectSummary> s3ObjectSummarieList = listing.getObjectSummaries();

        if(s3ObjectSummarieList!=null && s3ObjectSummarieList.size()>0){
            return s3ObjectSummarieList.get(0);
        }
        return null;
    }

    public static String getDownloadUrl(String containerId, String objectId){
        return getDownloadUrl(containerId, objectId,null);
    }

    public static String getDownloadUrl(String containerId, String objectId,String filename) {
        AmazonS3 s3Client = getAmazonS3Client();
        GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(bucketName, objectId);
        //设置过期时间
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);
        httpRequest.setExpiration(expiration);

        if(StringUtils.isNotBlank(filename)){
            String responseContentDisposition = null;
            try {
                responseContentDisposition = "attachment;filename=" + URLEncoder.encode(filename, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpRequest.addRequestParameter("response-content-disposition", responseContentDisposition);
        }
        return s3Client.generatePresignedUrl(httpRequest).toString();

    }

    private static AmazonS3 getAmazonS3Client(){
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =new AwsClientBuilder.EndpointConfiguration(serviceEndpoint,"");
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(endpointConfiguration)
                .build();
        return s3Client;
    }

    /**
     * 查询桶列表
     */
    public static void getBucketsTest() {
        AmazonS3 s3Client = getAmazonS3Client();
        List<Bucket> bucketList = s3Client.listBuckets();
        for (int i = 0; i < bucketList.size(); i++) {
            System.out.println(bucketList.get(i).getName());
        }
    }



    /**
     * 查询桶的文件
     */

    public static void listObjectsTest() {
        AmazonS3 s3Client = getAmazonS3Client();
        ListObjectsRequest listObjectRequest = new ListObjectsRequest();
        ObjectListing objectListing = null;
        listObjectRequest.setBucketName(bucketName);
        // listObjectRequest.setPrefix("DATALAKE/LF02/");

        int j=0;
        int num =0;
        do {
            objectListing = s3Client.listObjects(listObjectRequest);
            List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
            if (s3ObjectSummaries != null && s3ObjectSummaries.size() > 0) {
                for (int i = 0; i < s3ObjectSummaries.size(); i++) {
                    num =j*1000+i;
                    System.out.println(num+"|"+j+"|"+i + "|" + s3ObjectSummaries.get(i).getKey() + "|" + s3ObjectSummaries.get(i).getETag());
                }
            }
            j++;
            listObjectRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());
    }

    /**
     * 生成下载地址
     */

    public static void  genDownloadUrlTest  (){
        AmazonS3 s3Client = getAmazonS3Client();
        String key = "006MhkgZly1h3v106huudj30pu1dswue.jpg";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,1);
        Date expiration = calendar.getTime();
        URL url = s3Client.generatePresignedUrl(bucketName,key,expiration);
        String downloadUrl = url.getProtocol()+"://"+url.getHost()+url.getFile();
        System.out.println(downloadUrl);
    }

    /**
     * 一次上传
     */

    public static void putObjectTest(){
        AmazonS3 s3Client = getAmazonS3Client();
        String key = "123.jpg";
        File file = new File("D:/peixun/img/123.jpg");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,key,file);
        s3Client.putObject(putObjectRequest);
    }

    /**
     * 分片上传
     */

    public static void multiPutTest()  {
        AmazonS3 s3Client = getAmazonS3Client();
        //初始化上传，key是上传到minio的名字，也是一个单独键
        String key = "33333";
        //通过一个key初始化一个请求任务
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName,key);
        //初始化任务上传到s3客户端进行申请，然后返回申请结果
        InitiateMultipartUploadResult initiateMultipartUploadResult = s3Client.initiateMultipartUpload(initiateMultipartUploadRequest);
        if(initiateMultipartUploadResult!=null){
            String uploadId = initiateMultipartUploadResult.getUploadId();
            //上传文件
            UploadPartRequest uploadPartRequest = new UploadPartRequest().withUploadId(uploadId);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            List<PartETag> partETags = new ArrayList<>();
            //每次以5兆大小上传
            byte[] content = new byte[5*1024*1024];
            //要上传的文件路径
            String filePath = "D:\\software\\win2022-2023.zip";
            File file = new File(filePath);
            //利用文件读取流读取文件并上传文件
            try{
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int readSize = 0;
                int partNum =1;
                while ((readSize = bis.read(content)) > 0) {
                    uploadPartRequest.setPartSize(readSize);
                    uploadPartRequest.setPartNumber(partNum);
                    uploadPartRequest.setInputStream(new ByteArrayInputStream(content));
                    partNum++;
                    UploadPartResult  uploadPartResult= s3Client.uploadPart(uploadPartRequest);
                    partETags.add(uploadPartResult.getPartETag());
                    System.out.println("uploadPartResult:"+uploadPartResult.getPartNumber()+"|"+uploadPartResult.getETag()+"|"+uploadPartResult.getServerSideEncryption()+"|"+uploadPartResult.getPartETag());
                }


            }catch (Exception e){
                e.printStackTrace();
            }


            //合并提交
            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
            s3Client.completeMultipartUpload(compRequest);
        }
    }

    /**
     * 刪除文件
     */

    public static void delObject(){
        AmazonS3 s3Client = getAmazonS3Client();

        String key = "/test/test.txt";
        s3Client.deleteObject(bucketName,key);

    }

    /**
     * 分片上传
     */

    public static void multiPutTest2() {
        AmazonS3 s3Client = getAmazonS3Client();
        //初始化上传
        String key = "/test/multi.txt";
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName,key);
        InitiateMultipartUploadResult initiateMultipartUploadResult = s3Client.initiateMultipartUpload(initiateMultipartUploadRequest);
        if(initiateMultipartUploadResult!=null){
            String uploadId = initiateMultipartUploadResult.getUploadId();
            //上传文件
            UploadPartRequest uploadPartRequest = new UploadPartRequest().withUploadId(uploadId);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            List<PartETag> partETags = new ArrayList<>();
            byte[] content = new byte[5*1024*1024];
            String filePath = "d:\\360se13.1.1594.0.exe";
            File file = new File(filePath);
            try{
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int readSize = 0;
                int partNum =1;
                while ((readSize = bis.read(content)) > 0) {
                    uploadPartRequest.setPartSize(readSize);
                    uploadPartRequest.setPartNumber(partNum);
                    uploadPartRequest.setInputStream(new ByteArrayInputStream(content));
                    partNum++;
                    UploadPartResult  uploadPartResult= s3Client.uploadPart(uploadPartRequest);
                    partETags.add(uploadPartResult.getPartETag());
                    System.out.println("uploadPartResult:"+uploadPartResult.getPartNumber()+"|"+uploadPartResult.getETag()+"|"+uploadPartResult.getServerSideEncryption()+"|"+uploadPartResult.getPartETag());
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            //查詢分片
            ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName,key,uploadId);
            PartListing partListing = s3Client.listParts(listPartsRequest);
            if(partListing !=null ){
                List<PartSummary> partSummaries = partListing.getParts();
                for (PartSummary partSummary: partSummaries
                ) {
                    System.out.println(partSummary.getPartNumber()+"|"+partSummary.getETag()+"|"+partSummary.getSize());

                }
            }

            //合并提交
            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
            s3Client.completeMultipartUpload(compRequest);
        }
    }



    public static void getMetaData(){
        AmazonS3 s3Client = getAmazonS3Client();
        String key = "test";
        GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest(bucketName,key);
        ObjectMetadata objectMetadata = s3Client.getObjectMetadata(getObjectMetadataRequest);

        Map<String,String> metaData = objectMetadata.getUserMetadata();
    }


    public static void main(String[] args) {


//       getBucketsTest();

       putObjectTest();

//        multiPutTest();


       // listObjectsTest();
//       genDownloadUrlTest();

       //  System.out.println("uuuuu======"+url);



    }
}
