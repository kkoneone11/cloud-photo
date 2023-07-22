package com.cloud.photo.trans.service;

import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.CommonEnum;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：kkoneone11
 * @name：IPutUploadUrlService
 * @Date：2023/7/20 10:52
 */

public interface IPutUploadUrlService {

    /**
     * 获得上传地址
     * @param fileName
     * @param fileSize
     * @param fileMd5
     * @return String地址
     */
    String getPutUploadUrl(@RequestParam(value = "fileName") String fileName,
                           @RequestParam(value = "fileSize") Long fileSize,
                           @RequestParam(value = "fileMd5") String fileMd5);

    /**
     * 非秒传方法
     * @param fileUploadBo
     * @return
     */
    CommonEnum commit(@RequestBody FileUploadBo fileUploadBo);


    /**
     * 秒传方法
     * @param fileUploadBo
     * @return
     */
    CommonEnum commitTransSecond(@RequestBody FileUploadBo fileUploadBo);
}
