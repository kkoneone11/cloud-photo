package com.cloud.photo.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.ObjectUtil;
import com.cloud.photo.api.service.PutuploadService;
import com.cloud.photo.api.utils.MyUtils;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相册相关接口
 * @author kkoneone11
 */
@RestController
@RequestMapping("/api")
public class PutuploadController {

    @Autowired
    PutuploadService putuploadService;


    /**
     * 获取上传地址
     * @return 每一张图片一个上传地址
     */
    @SaCheckLogin
    @PostMapping("/getPutUploadUrl")
    public ResultBody getPutUploadUrl(@RequestBody FileUploadBo fileUploadBo){
        //从缓存拿到用户信息
        UserBo userInfo = MyUtils.getUserInfo();
        if (userInfo == null){
            return ResultBody.error(CommonEnum.USER_IS_NULL);
        }
        //用户ID
        String userId = userInfo.getUserId();
        //判断下上传的文件是否为空
        if (fileUploadBo == null){
            return ResultBody.error(CommonEnum.FILE_LIST_IS_NULL);
        }
        //拼接用户ID
        fileUploadBo.setUserId(userId);

        // 业务实现
        return putuploadService.getPutUploadUrl(fileUploadBo);
    }

    /**
     * 提交
     * @return 结果
     */
    @SaCheckLogin
    @PostMapping("/commitUpload")
    public ResultBody commitUpload(@RequestBody FileUploadBo bo){
        //从缓存拿到用户信息
        UserBo userInfo = MyUtils.getUserInfo();
        if (userInfo == null){
            return ResultBody.error(CommonEnum.USER_IS_NULL);
        }
        //用户ID
        String userId = userInfo.getUserId();
        //判断下上传的文件是否为空
        if (ObjectUtil.isEmpty(bo)){
            return ResultBody.error(CommonEnum.FILE_LIST_IS_NULL);
        }
        //拼接用户ID
        bo.setUserId(userId);

        // 业务实现
        return putuploadService.commitUpload(bo);
    }


}
