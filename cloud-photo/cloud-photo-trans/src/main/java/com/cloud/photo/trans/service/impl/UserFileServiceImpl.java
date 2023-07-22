package com.cloud.photo.trans.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.trans.entity.UserFile;
import com.cloud.photo.trans.mapper.UserFileMapper;
import com.cloud.photo.trans.service.IUserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-20
 */
@Service
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements IUserFileService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public boolean saveAndFileDeal(FileUploadBo fileUploadBo) {
        UserFile userFile = new UserFile();
        userFile.setUserId(fileUploadBo.getUserId());
        userFile.setFileStatus(CommonConstant.FILE_STATUS_NORMA); //文件正常
        userFile.setCreateTime(LocalDateTime.now());
        userFile.setFileName(fileUploadBo.getFileName());
        userFile.setIsFolder(CommonConstant.FILE_IS_FOLDER_NO);
        userFile.setAuditStatus(CommonConstant.FILE_AUDIT_ACCESS);
        userFile.setFileSize(fileUploadBo.getFileSize());
        userFile.setModifyTime(LocalDateTime.now());
        userFile.setStorageObjectId(fileUploadBo.getStorageObjectId());
        userFile.setCategory(fileUploadBo.getCategory());
        Boolean result =  this.save(userFile);

        //图片处理-格式分析。将其转化为JSON格式的字符传去主题为FILE_AUDIT_TOPIC
        kafkaTemplate.send(CommonConstant.FILE_AUDIT_TOPIC , JSONObject.toJSONString(userFile));
        //文件审核处理将其转化为JSON格式的字符传去主题为FILE_IMAGE_TOPIC
        kafkaTemplate.send(CommonConstant.FILE_IMAGE_TOPIC , JSONObject.toJSONString(userFile));


        return result;
    }
}
