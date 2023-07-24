package com.cloud.photo.api.service;


import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相册相关接口
 * @author linzsh
 */
@RestController
public interface PutuploadService {

    /**
     * 获取上传地址
     * @param bo 图片上传信息实体
     * @return 每一张图片一个上传地址
     */
    ResultBody getPutUploadUrl(FileUploadBo bo);

    /**
     * 提交
     * @param bo 图片上传信息实体
     * @return 结果
     */
    ResultBody commitUpload(FileUploadBo bo);

}
