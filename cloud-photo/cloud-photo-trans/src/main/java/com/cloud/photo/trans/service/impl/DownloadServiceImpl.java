package com.cloud.photo.trans.service.impl;

import com.cloud.photo.common.utils.S3Util;
import com.cloud.photo.trans.entity.StorageObject;
import com.cloud.photo.trans.entity.UserFile;
import com.cloud.photo.trans.service.IDownloadService;
import com.cloud.photo.trans.service.IStorageObjectService;
import com.cloud.photo.trans.service.IUserFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：kkoneone11
 * @name：DownloadServiceImpl
 * @Date：2023/7/21 14:48
 */
@Slf4j
@Service
public class DownloadServiceImpl implements IDownloadService {

    @Autowired
    IUserFileService iUserFileService;

    @Autowired
    IStorageObjectService iStorageObjectService;

    /**
     * 通过FileId获得文件上传地址
     * @param userId
     * @param fileId
     * @return
     */
    @Override
    public String getDownloadUrlByFileId(String userId, String fileId) {
        UserFile userFile = iUserFileService.getById(fileId);

        //如果目标不存在则打印一下然后返回null即可
        if(userFile == null){
            log.error("getDownloadUrlByFileid() userFile is null,fileId = "+ fileId );
            return null;
        }

        //目标存在则取出containId和objectId进行查询
        StorageObject storageObject = iStorageObjectService.getById(userFile.getStorageObjectId());
        if(storageObject == null){
            log.error("getDownloadUrlByFileid() storageObject is null,fileId"+ fileId );
            return null;
        }

        return S3Util.getDownloadUrl(storageObject.getContainerId(),storageObject.getObjectId());
    }

    @Override
    public String getDownloadUrl(String containerId, String objectId) {
        return S3Util.getDownloadUrl(containerId,objectId);
    }
}
