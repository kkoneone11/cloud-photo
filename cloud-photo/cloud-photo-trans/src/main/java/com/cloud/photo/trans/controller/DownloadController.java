package com.cloud.photo.trans.controller;

import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.utils.RequestUtil;
import com.cloud.photo.trans.service.IDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author：kkoneone11
 * @name：DownloadController
 * @Date：2023/7/21 12:38
 */


@RestController
@Slf4j
@RequestMapping("/trans")
public class DownloadController {

    @Autowired
    IDownloadService iDownloadService;

    /**
     * 通过fileId获得下载地址
     * @param request
     * @param response
     * @param userId
     * @param fileId
     * @return
     */
    @RequestMapping("/getDownloadUrlByFileId")
    public ResultBody getDownloadUrlByFileId(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "fileId") String fileId){
        //打印一下这次请求
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);

        String url = iDownloadService.getDownloadUrlByFileId(userId,fileId);
        log.info("getDownloadUrlByFileid() userId = " + userId + " , fileId = " + fileId +", url = " + url);
        return ResultBody.success(url);
    }

    @RequestMapping()
    public ResultBody getDownloadUrl(HttpServletRequest request,HttpServletResponse response,
                                     @RequestParam( value = "containerId") String containerId,
                                     @RequestParam( value = "objectId")String objectId){
        //打印一下这次请求
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);

        String url = iDownloadService.getDownloadUrl(containerId, objectId);
        log.info("getDownloadUrl() url = "+url);
        return ResultBody.success(url);
    }
}
