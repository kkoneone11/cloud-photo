package com.cloud.photo.image.controller;

import com.cloud.photo.common.bo.FileResizeIconBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.utils.RequestUtil;
import com.cloud.photo.image.service.IFileResizeIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 图片缩略图 前端控制器
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-22
 */
@RestController
@RequestMapping("/image")
public class FileResizeIconController {

    @Autowired
    private IFileResizeIconService iFileResizeIconService;

    @RequestMapping("/getIconUrl")
    public ResultBody getIconUrl(HttpServletRequest request, HttpServletResponse response,
                                 @RequestBody FileResizeIconBo fileResizeIconBo){
        //打印一下这次请求
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);



        return null;
    }

}
