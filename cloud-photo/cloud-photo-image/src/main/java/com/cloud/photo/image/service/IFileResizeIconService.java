package com.cloud.photo.image.service;

import com.cloud.photo.image.entity.FileResizeIcon;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.File;

/**
 * <p>
 * 图片缩略图 服务类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-22
 */
public interface IFileResizeIconService extends IService<FileResizeIcon> {

    String getIconUrl(String userId, String fileId, String iconCode);

    FileResizeIcon getFileResizeIcon(String storageObjectId, String iconCode);

    String downloadImage(String containerId, String objectId, String suffixName);

    FileResizeIcon imageThumbnailSave(String iconCode , String suffixName , String srcFileName,
                                             String storageObjectId , String fileName);

    FileResizeIcon uploadIcon(String userId, String storageObjectId , String iconCode, File iconFile, String fileName);

    void imageThumbnailAndMediaInfo(String storageObjectId, String fileName);

    String getAuditFailIconUrl();
}
