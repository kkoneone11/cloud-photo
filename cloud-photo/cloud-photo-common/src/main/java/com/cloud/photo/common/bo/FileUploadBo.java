package com.cloud.photo.common.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 上传文件信息体
 * @author linzsh
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadBo {

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件MD5
     */
    private String fileMd5;
    /**
     * 后面拼接用户ID
     */
    private String userId;
    /**
     * 对象ID
     */
    private String objectId;
    /**
     * 资源池ID
     */
    private String containerId;
    /**
     * 对象ID(秒传用)
     */
    private String storageObjectId;
    /**
     * 上传地址
     */
    private String uploadUrl;
    /**
     * 分类
     */
    private Integer category;

    /**
     * base64文件md5
     */
    private String base64Md5;

    /**
     * 上传状态
     */
    private String status;

    /**
     * 上传时间
     */
    private String uploadTime;
}
