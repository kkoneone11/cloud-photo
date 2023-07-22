package com.cloud.photo.common.bo;

import lombok.Data;

/**
 * 图片格式分析实体
 * @author acer
 */
@Data
public class FileResizeIconBo {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 图片ID
     */
    private String fileId;

    /**
     * 缩略图尺寸(200_200)
     */
    private String iconCode;
}
