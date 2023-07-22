package com.cloud.photo.trans.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.utils.S3Util;
import com.cloud.photo.trans.entity.FileMd5;
import com.cloud.photo.trans.entity.StorageObject;
import com.cloud.photo.trans.service.IFileMd5Service;
import com.cloud.photo.trans.service.IPutUploadUrlService;
import com.cloud.photo.trans.service.IStorageObjectService;
import com.cloud.photo.trans.service.IUserFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：kkoneone11
 * @name：PutUploadUrlServiceImpl
 * @Date：2023/7/20 10:54
 */
@Service
public class PutUploadUrlServiceImpl implements IPutUploadUrlService {

    @Autowired
    IFileMd5Service iFileMd5Service;

    @Autowired
    IStorageObjectService iStorageObjectService;

    @Autowired
    IUserFileService iUserFileService;



    @Override
    public String getPutUploadUrl(String fileName, Long fileSize, String fileMd5) {
        //先根据Md5看看是否存在数据。存在即秒传
        FileMd5 fileMd5Entity = iFileMd5Service.getOne(new QueryWrapper<FileMd5>().eq("md5",fileMd5).eq("file_size",fileSize));

        //如果不为空就将文件的storageId在哪个地方的信息转化为JSON格式再进行秒传
        if(fileMd5Entity != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("storageObjectId",fileMd5Entity.getStorageObjectId());
            return jsonObject.toJSONString();
        }
        //为空则根据fileName的后缀名和Md5生成一个唯一的上传地址,而fileName实际也可以为空，因此suffixName初始化为空
        String suffixName = "";

        if(StringUtils.isNotBlank(fileName)){
            suffixName = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        }

        return S3Util.getPutUploadUrl(suffixName, fileMd5);
    }

    /**
     * 非秒传
     * @param fileUploadBo
     * @return
     */
    @Override
    public CommonEnum commit(FileUploadBo fileUploadBo) {
        //因为有可能生成了上传地址但还没有上传图片，如果上传了照片的话在资源池中会有一个objectId,所以先看看有没有objectId
        S3ObjectSummary s3ObjectSummary = S3Util.getObjectInfo(fileUploadBo.getObjectId());
        //1.没有上传
        if(s3ObjectSummary == null){
            return CommonEnum.FILE_NOT_UPLOADED;
        }
        //2.没有上传到正确的文件  文件大小不一致、md5与ETag不一致
        if(!fileUploadBo.getFileSize().equals(s3ObjectSummary.getSize())||
            !StringUtils.equalsIgnoreCase(s3ObjectSummary.getETag(),fileUploadBo.getFileMd5())){
            return CommonEnum.FILE_UPLOADED_ERROR;
        }
        //入库 - StorageObject表
        StorageObject storageObject = new StorageObject("minio", fileUploadBo.getContainerId(), fileUploadBo.getObjectId(),
                fileUploadBo.getFileMd5(), fileUploadBo.getFileSize());
        iStorageObjectService.save(storageObject);

        //入库 - MD5表
        FileMd5 fileMd5 = new FileMd5(fileUploadBo.getFileMd5(), fileUploadBo.getFileSize(), fileUploadBo.getStorageObjectId());
        iFileMd5Service.save(fileMd5);

        //入库 - UserFile表
        fileUploadBo.setStorageObjectId(storageObject.getStorageObjectId());
        iUserFileService.saveAndFileDeal(fileUploadBo);

        return CommonEnum.SUCCESS;
    }

    /**
     * 秒传
     * @param fileUploadBo
     * @return
     */
    @Override
    public CommonEnum commitTransSecond(FileUploadBo fileUploadBo) {
        //如果已经存入过的话会有一个StorageObjectId生成，因此用这个来判断到底是否存在和上传的是否一致即可
        StorageObject storageObject = iStorageObjectService.getById(fileUploadBo.getStorageObjectId());

        //1.传入的不在数据库中
        if(storageObject == null){
            return  CommonEnum.FILE_UPLOADED_ERROR;
        }

        //2.传入的和存在数据库的信息不一致
        if(!storageObject.getObjectSize().equals(fileUploadBo.getFileSize())
                || !StringUtils.equalsIgnoreCase(fileUploadBo.getFileMd5(),storageObject.getMd5())){
            return CommonEnum.FILE_UPLOADED_ERROR;
        }
        //入库
        //将文件入库（保存文件） - 用户文件列表 - 分别发送到审核、图片kafka列表
        fileUploadBo.setStorageObjectId(storageObject.getStorageObjectId());
        iUserFileService.saveAndFileDeal(fileUploadBo);
        return CommonEnum.SUCCESS;
    }
}
