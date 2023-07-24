package com.cloud.photo.api.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.photo.api.service.PutuploadService;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.common.fegin.Cloud2TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author weifucheng
 */
@Service
public class PutuploadServiceImpl implements PutuploadService {

    @Autowired
    private Cloud2TransService cloud2TransService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResultBody getPutUploadUrl(FileUploadBo fileUploadBo) {

        ResultBody resultBody = cloud2TransService.getPutUploadUrl(fileUploadBo.getUserId(),
                fileUploadBo.getFileName(),
                fileUploadBo.getFileMd5(),
                fileUploadBo.getFileSize());

        //存进缓存的Key
        String key = fileUploadBo.getUserId() + ":" +  fileUploadBo.getFileMd5();

        if (resultBody.getCode().equals(CommonEnum.SUCCESS.getResultCode())) {
            //成功
            JSONObject obj = JSONUtil.parseObj(resultBody.getData());
            if (obj.containsKey("storageObjectId")) {
                //是秒传
                fileUploadBo.setStorageObjectId(obj.getStr("storageObjectId"));
            } else {
                //不是秒传
                fileUploadBo.setContainerId(obj.getStr("containerId"));
                fileUploadBo.setObjectId(obj.getStr("objectId"));
                fileUploadBo.setUploadUrl(obj.getStr("url"));
                fileUploadBo.setBase64Md5(obj.getStr("base64Md5"));
            }

            //成功了状态是传输中
            fileUploadBo.setStatus(CommonConstant.FILE_UPLOAD_ING);
            fileUploadBo.setUploadTime(DateUtil.now());
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(fileUploadBo), 1, TimeUnit.DAYS);
        }else {
            //失败了状态是传输失败
            fileUploadBo.setStatus(CommonConstant.FILE_UPLOAD_FAIL);
            fileUploadBo.setUploadTime(DateUtil.now());
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(fileUploadBo), 1, TimeUnit.DAYS);
        }

        return ResultBody.success(fileUploadBo);
    }

    @Override
    public ResultBody commitUpload(FileUploadBo bo) {

        //存进缓存的Key
        String key = bo.getUserId() + ":" +  bo.getFileMd5();

        ResultBody commitResultBody = cloud2TransService.commit(bo);

        if (commitResultBody.getCode().equals(CommonEnum.SUCCESS.getResultCode())){
            //成功提交 - 状态更新为传输成功
            bo.setStatus(CommonConstant.FILE_UPLOAD_SUCCESS);
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(bo), 1, TimeUnit.DAYS);
        }else {
            //提及失败 - 状态更新为传输失败
            bo.setStatus(CommonConstant.FILE_UPLOAD_FAIL);
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(bo), 1, TimeUnit.DAYS);
        }

        return commitResultBody;
    }

}
