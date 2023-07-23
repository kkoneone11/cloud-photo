package com.cloud.photo.audit.service;

import com.cloud.photo.audit.entity.FileAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.photo.common.bo.AuditPageBo;

/**
 * <p>
 * 文件审核列表 服务类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-23
 */
public interface IFileAuditService extends IService<FileAudit> {

    Boolean updateAuditStatus(AuditPageBo pageBo);
}
