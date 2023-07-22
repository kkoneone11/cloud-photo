package com.cloud.photo.common.bo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 资源池文件存储信息
 * </p>
 *
 * @author weifucheng
 * @since 2023-06-13
 */
@Data
@ToString
public class StorageObjectBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String storageObjectId;

    /**
     * 存储池供应商
     */
    private String storageProvider;

    /**
     * 桶ID
     */
    private String containerId;

    /**
     * 存储池文件id
     */
    private String objectId;

    private String md5;

    private Long objectSize;

    public StorageObjectBo(){}

}
