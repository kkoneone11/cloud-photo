package com.cloud.photo.trans.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.photo.common.bo.AlbumPageBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.utils.RequestUtil;
import com.cloud.photo.trans.entity.UserFile;
import com.cloud.photo.trans.service.IUserFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-20
 */
@RestController
@RequestMapping("/trans")
public class UserFileController {
    @Autowired
    IUserFileService iUserFileService;

    @RequestMapping("/userFilelist")
    public ResultBody userFilelist(HttpServletRequest request , HttpServletResponse response,
                                   @RequestBody AlbumPageBo albumPageBo){

        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request,albumPageBo);

        //设置QueryWrapper
        QueryWrapper<UserFile> qw = new QueryWrapper<>();
        //通过HashMap组装多个条件Wrapper
        HashMap<String,Object> hm = new HashMap<>();
        if(albumPageBo.getCategory()!=null){
            hm.put("category" , albumPageBo.getCategory());
        }
        hm.put("userId", albumPageBo.getUserId());
        qw.allEq(hm);
        Integer pageSize = albumPageBo.getPageSize();
        Integer current = albumPageBo.getCurrent();
        if(current == null) current = 1;
        if(pageSize == null ) pageSize = 20;
        //组装一下page
        Page<UserFile> page = new Page<UserFile>(current, pageSize);
        IPage<UserFile> userFilePage = iUserFileService.page(page, qw.orderByDesc("user_id", "create_time"));

        return ResultBody.success(userFilePage);
    }

    @RequestMapping("/updateUserFile")
    public Boolean updateUserFile(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody List<UserFile> userFileBoList){
        //打印这次请求信息
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);

        //取出每个userFile分别进行更新
        for(UserFile userFile : userFileBoList){
            UpdateWrapper<UserFile> updateWrapper = new UpdateWrapper<>();
            //根据StorageObjectId条件更新审核
            if(StringUtils.isNotBlank(userFile.getStorageObjectId())){
                updateWrapper.eq("storage_object_id",userFile.getStorageObjectId());
            }
            //添加userfileid条件更新审核
            if(StringUtils.isNotBlank(userFile.getUserFileId())){
                updateWrapper.eq("user_file_id",userFile.getUserFileId());
            }
            //设置更新的状态
            updateWrapper.set("audit_status",userFile.getAuditStatus());
            //执行pdateWrapper
            iUserFileService.update(updateWrapper);

        }

        return true;
    }
}
