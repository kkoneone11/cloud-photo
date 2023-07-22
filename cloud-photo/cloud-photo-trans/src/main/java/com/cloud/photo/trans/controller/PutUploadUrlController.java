package com.cloud.photo.trans.controller;

import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.utils.RequestUtil;
import com.cloud.photo.trans.service.IPutUploadUrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：kkoneone11
 * @name：PutUploadUrlController
 * @Date：2023/7/20 9:57
 */
@RestController
@Slf4j
@RequestMapping("/trans")
public class PutUploadUrlController {

    @Autowired
    IPutUploadUrlService iPutUploadUrlService;

    /**
     *
     * @param request
     * @param response
     * @param userId
     * @param fileName
     * @param fileMd5
     * @param fileSize
     * @return
     */
    @GetMapping("/getPutUploadUrl")
    public ResultBody getPutUploadUrl(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "userId",required = false) String userId,
                                      @RequestParam(value = "fileName") String fileName,
                                      @RequestParam(value = "fileMd5",required = false) String fileMd5,
                                      @RequestParam(value = "fileSize",required = false) Long fileSize
                                      ){
        //打印处理时间和处理的请求
        Long startTime = System.currentTimeMillis();
        String requestId = RequestUtil.getRequestId(request);
        String result = iPutUploadUrlService.getPutUploadUrl(fileName, fileSize, fileMd5);
        Long endTime = System.currentTimeMillis();

        //打印一下信息
        log.info("getPutUploadUrl() , userId = "+ userId + ",result" + result,", cost" + (endTime-startTime));

       return ResultBody.success(result,requestId);
    }

    @PostMapping("/commit")
    public ResultBody commit(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody FileUploadBo fileUploadBo){

        //打印一下这次的请求和返回来的消息
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request,fileUploadBo);

        CommonEnum result;
        //根据StoreObjectId是否已经入库判断是否需要秒传
        //1.非秒传 调用
        if(StringUtils.isBlank(fileUploadBo.getStorageObjectId())){
            result = iPutUploadUrlService.commit(fileUploadBo);
        }else {
        //2.秒传 commitTransSecond
            result = iPutUploadUrlService.commitTransSecond(fileUploadBo);
        }


        //打印相关信息
        log.info("getPutUploadUrl() userId = " + fileUploadBo.getUserId() + " , result = " + result);
        if (StringUtils.equals(result.getResultMsg(), CommonEnum.SUCCESS.getResultMsg())) {
            return ResultBody.success(CommonEnum.SUCCESS.getResultMsg(), requestId);
        } else {
            return ResultBody.error(result.getResultCode(), result.getResultMsg(), requestId);
        }
    }


}
