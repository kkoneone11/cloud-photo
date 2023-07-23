package com.cloud.photo.audit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.photo.audit.entity.FileAudit;
import com.cloud.photo.audit.service.IFileAuditService;
import com.cloud.photo.common.bo.AuditPageBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文件审核列表 前端控制器
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-23
 */
@RestController
@Slf4j
@RequestMapping("/audit")
public class FileAuditController {

    @Autowired
    private IFileAuditService iFileAuditService;

    /**
     * 查询列表
     * @param pageBo
     * @return
     */
    @PostMapping("/getAuditList")
    public ResultBody getAuditList(@RequestBody AuditPageBo pageBo){
        //1.设置查询Mapper
        QueryWrapper<FileAudit> qw = new QueryWrapper<FileAudit>();

        //不为空则查询的是
        if(pageBo.getAuditStatusList() != null && pageBo.getAuditStatusList().size()>0){
            qw.in("audit_status",pageBo.getAuditStatusList());
        }else{
        //为空则查询的是没有审核
            qw.eq("audit_status", CommonConstant.FILE_AUDIT);
        }

        //设置page
        Integer current = pageBo.getCurrent();
        Integer pageSize = pageBo.getPageSize();
        if(current == null){
            current = 1;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        Page<FileAudit> page = new Page<FileAudit>(current, pageSize);
        IPage<FileAudit> userFileIPage = iFileAuditService.page(page,qw.orderByAsc("create_time"));
        return ResultBody.success(userFileIPage);
    }


    /**
     * 更新审核状态
     * @param pageBo
     * @return
     */
    @PostMapping("/updateAuditStatus")
    public ResultBody updateAuditStatus(@RequestBody AuditPageBo pageBo){

        Boolean result = iFileAuditService.updateAuditStatus(pageBo);

        return  ResultBody.success(result);
    }


}
