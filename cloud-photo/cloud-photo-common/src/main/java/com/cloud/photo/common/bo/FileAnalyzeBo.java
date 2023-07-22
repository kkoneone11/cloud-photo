package com.cloud.photo.common.bo;

import lombok.Data;

/**
 * 图片格式分析实体
 * @author acer
 */
@Data
public class FileAnalyzeBo {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 图片ID
     */
    private String fileId;
}
