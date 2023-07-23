package com.cloud.photo.image.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 图片缩略图
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-22
 */
@TableName("tb_file_resize_icon")
public class FileResizeIcon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String fileResizeIconId;

    /**
     * 图片存储id
     */
    private String storageObjectId;

    /**
     * 图片尺寸(240_240,600_600)
     */
    private String iconCode;

    /**
     * 缩略图存储桶
     */
    private String containerId;

    /**
     * 缩略图存储id
     */
    private String objectId;

    public FileResizeIcon(){}

    public FileResizeIcon(String storageObjectId, String iconCode, String containerId, String objectId) {
        this.storageObjectId = storageObjectId;
        this.iconCode = iconCode;
        this.containerId = containerId;
        this.objectId = objectId;
    }

    public String getFileResizeIconId() {
        return fileResizeIconId;
    }

    public void setFileResizeIconId(String fileResizeIconId) {
        this.fileResizeIconId = fileResizeIconId;
    }

    public String getStorageObjectId() {
        return storageObjectId;
    }

    public void setStorageObjectId(String storageObjectId) {
        this.storageObjectId = storageObjectId;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "FileResizeIcon{" +
            "fileResizeIconId = " + fileResizeIconId +
            ", storageObjectId = " + storageObjectId +
            ", iconCode = " + iconCode +
            ", containerId = " + containerId +
            ", objectId = " + objectId +
        "}";
    }
}
