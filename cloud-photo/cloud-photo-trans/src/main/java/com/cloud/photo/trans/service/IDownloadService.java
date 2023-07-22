package com.cloud.photo.trans.service;

/**
 * @Author：kkoneone11
 * @name：IDownloadService
 * @Date：2023/7/21 14:40
 */
public interface IDownloadService {

    String getDownloadUrlByFileId(String userId , String fileId);
}
