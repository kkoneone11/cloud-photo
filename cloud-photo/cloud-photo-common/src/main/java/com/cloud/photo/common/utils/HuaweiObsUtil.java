package com.cloud.photo.common.utils;

import com.obs.services.ObsClient;
import com.obs.services.model.BucketCors;
import com.obs.services.model.BucketCorsRule;

import java.util.ArrayList;
import java.util.List;

public class HuaweiObsUtil {

    public static void main(String[] args) {

        String ak = "root";
        String sk = "Dsz202307";
        String bucketName = "disanzu-66";
        String endPoint = "http://disanzu-66.obs.cn-south-1.myhuaweicloud.com";


        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        BucketCors cors = new BucketCors();

        List<BucketCorsRule> rules = new ArrayList<BucketCorsRule>();
        BucketCorsRule rule = new BucketCorsRule();

        ArrayList<String> allowedOrigin = new ArrayList<String>();
        // 指定允许跨域请求的来源
        allowedOrigin.add( "*");
        rule.setAllowedOrigin(allowedOrigin);

        ArrayList<String> allowedMethod = new ArrayList<String>();
        // 指定允许的跨域请求方法(GET/PUT/DELETE/POST/HEAD)
        allowedMethod.add("GET");
        allowedMethod.add("HEAD");
        allowedMethod.add("PUT");
        rule.setAllowedMethod(allowedMethod);

        ArrayList<String> allowedHeader = new ArrayList<String>();
        // 控制在OPTIONS预取指令中Access-Control-Request-Headers头中指定的header是否被允许使用
        allowedHeader.add("*");
        rule.setAllowedHeader(allowedHeader);

        ArrayList<String> exposeHeader = new ArrayList<String>();
        // 指定允许用户从应用程序中访问的header
        exposeHeader.add("x-obs-expose-header");
        rule.setExposeHeader(exposeHeader);

        // 指定浏览器对特定资源的预取(OPTIONS)请求返回结果的缓存时间,单位为秒
        rule.setMaxAgeSecond(100);
        rules.add(rule);
        cors.setRules(rules);

        obsClient.setBucketCors(bucketName, cors);

        BucketCors bucketCors = obsClient.getBucketCors(bucketName);
        System.out.println(bucketCors.toString());
    }
}
