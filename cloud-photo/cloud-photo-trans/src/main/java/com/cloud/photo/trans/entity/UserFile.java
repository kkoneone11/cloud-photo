package com.cloud.photo.trans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-20
 */
@TableName("tb_user_file")
public class UserFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String userFileId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 父目录id
     */
    private String parentId;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件状态(1 正常 2删除)
     */
    private String fileStatus;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 是否是目录
     */
    private String isFolder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 存储id
     */
    private String storageObjectId;

    /**
     * 文件分类
     */
    private Integer category;

    /**
     * 审核状态(00待审核 01 审核通过 02 审核不通过)
     */
    private Integer auditStatus;

    public String getUserFileId() {
        return userFileId;
    }

    public void setUserFileId(String userFileId) {
        this.userFileId = userFileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getStorageObjectId() {
        return storageObjectId;
    }

    public void setStorageObjectId(String storageObjectId) {
        this.storageObjectId = storageObjectId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public String toString() {
        return "UserFile{" +
            "userFileId = " + userFileId +
            ", userId = " + userId +
            ", fileName = " + fileName +
            ", parentId = " + parentId +
            ", fileSize = " + fileSize +
            ", fileStatus = " + fileStatus +
            ", fileType = " + fileType +
            ", isFolder = " + isFolder +
            ", createTime = " + createTime +
            ", modifyTime = " + modifyTime +
            ", storageObjectId = " + storageObjectId +
            ", category = " + category +
            ", auditStatus = " + auditStatus +
        "}";
    }
}
