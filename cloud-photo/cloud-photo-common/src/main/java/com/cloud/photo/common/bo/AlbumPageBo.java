package com.cloud.photo.common.bo;

import lombok.Data;

/**
 * @author linzsh
 */
@Data
public class AlbumPageBo {

    /**
     * 拼接的用户ID
     */
    private String userId;
    /**
     * 当前页
     */
    private Integer current;
    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 文件分类
     */
    private Integer category;
}
