package com.cloud.photo.audit.service.impl;

import com.cloud.photo.audit.entity.FileAudit;
import com.cloud.photo.audit.mapper.FileAuditMapper;
import com.cloud.photo.audit.service.IFileAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.photo.common.bo.AuditPageBo;
import com.cloud.photo.common.bo.UserFileBo;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.common.fegin.Cloud2TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文件审核列表 服务实现类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-23
 */
@Service
public class FileAuditServiceImpl extends ServiceImpl<FileAuditMapper, FileAudit> implements IFileAuditService {

    @Autowired
    private IFileAuditService iFileAuditService;

    @Autowired
    private Cloud2TransService cloud2TransService;
    @Override
    public Boolean updateAuditStatus(AuditPageBo auditPageBo) {
        Integer auditStatus = auditPageBo.getAuditStatus();
        List<String> idsList = auditPageBo.getFileAuditIds();

        List<UserFileBo> userFileBoList = new ArrayList<>();
        List<FileAudit> fileAuditList = iFileAuditService.listByIds(idsList);
        for(FileAudit fileAudit : fileAuditList){
            fileAudit.setAuditStatus(auditStatus);

            //组装UserFileBo去更新状态
            UserFileBo userFileBo = new UserFileBo();
            userFileBo.setAuditStatus(auditStatus);
            userFileBo.setStorageObjectId(fileAudit.getStorageObjectId());
            userFileBoList.add(userFileBo);
        }

        Boolean updateResult = iFileAuditService.updateBatchById(fileAuditList);

        //审核不通过的才去更新更新文件列表审核状态
        if(auditStatus.equals(CommonConstant.FILE_AUDIT_FAIL)){
            updateResult = cloud2TransService.updateUserFile(userFileBoList);
        }

        return updateResult;
    }
}
