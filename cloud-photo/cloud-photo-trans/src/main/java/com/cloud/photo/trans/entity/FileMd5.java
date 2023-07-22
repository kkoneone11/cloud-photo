package com.cloud.photo.trans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 文件MD5列表
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-20
 */
@TableName("tb_file_md5")
public class FileMd5 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String fileMd5Id;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 存储id
     */
    private String storageObjectId;
    public FileMd5(){}

    public FileMd5(String fileMd5, Long fileSize, String storageObjectId) {
        this.md5 = fileMd5;
        this.fileSize = fileSize;
        this.storageObjectId = storageObjectId;
    }

    public String getFileMd5Id() {
        return fileMd5Id;
    }

    public void setFileMd5Id(String fileMd5Id) {
        this.fileMd5Id = fileMd5Id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStorageObjectId() {
        return storageObjectId;
    }

    public void setStorageObjectId(String storageObjectId) {
        this.storageObjectId = storageObjectId;
    }

    @Override
    public String toString() {
        return "FileMd5{" +
            "fileMd5Id = " + fileMd5Id +
            ", md5 = " + md5 +
            ", fileSize = " + fileSize +
            ", storageObjectId = " + storageObjectId +
        "}";
    }
}
