package com.cloud.photo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Description:
 * 生成requestId
 * @date 2023.4.27
 * @author weifucheng
 */
@Slf4j
public class RequestUtil {

    public static String getRequestId(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-ID");
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString().replaceAll("-", "");
        }
        MDC.put("logId", requestId);
        return requestId;
    }

    public static void printQequestInfo(HttpServletRequest request) {
        log.info("printQequestInfo() -  paras={}", request.getQueryString());
    }
    public static void printQequestInfo(HttpServletRequest request,Object requestBody) {
        log.info("printQequestInfo() -  paras={},requestBody ={}", request.getQueryString() , requestBody);
    }
}
