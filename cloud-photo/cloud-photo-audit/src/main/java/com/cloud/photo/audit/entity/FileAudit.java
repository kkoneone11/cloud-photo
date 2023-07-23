package com.cloud.photo.audit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件审核列表
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-23
 */
@TableName("tb_file_audit")
public class FileAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String fileAuditId;

    private String fileName;

    private String md5;

    private Integer fileSize;

    private String storageObjectId;

    private Integer auditStatus;

    private String remark;

    private LocalDateTime createTime;

    private String userFileId;

    public String getFileAuditId() {
        return fileAuditId;
    }

    public void setFileAuditId(String fileAuditId) {
        this.fileAuditId = fileAuditId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getStorageObjectId() {
        return storageObjectId;
    }

    public void setStorageObjectId(String storageObjectId) {
        this.storageObjectId = storageObjectId;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUserFileId() {
        return userFileId;
    }

    public void setUserFileId(String userFileId) {
        this.userFileId = userFileId;
    }

    @Override
    public String toString() {
        return "FileAudit{" +
            "fileAuditId = " + fileAuditId +
            ", fileName = " + fileName +
            ", md5 = " + md5 +
            ", fileSize = " + fileSize +
            ", storageObjectId = " + storageObjectId +
            ", auditStatus = " + auditStatus +
            ", remark = " + remark +
            ", createTime = " + createTime +
            ", userFileId = " + userFileId +
        "}";
    }
}
