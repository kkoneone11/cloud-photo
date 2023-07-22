package com.cloud.photo.common.bo;

import lombok.Data;

import java.util.List;

/**
 * @author linzsh
 */
@Data
public class AuditPageBo {

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
     * 审核状态-多个
     */
    private List<Integer> auditStatusList;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 审核IDs
     */
    private List<String> fileAuditIds;
}
