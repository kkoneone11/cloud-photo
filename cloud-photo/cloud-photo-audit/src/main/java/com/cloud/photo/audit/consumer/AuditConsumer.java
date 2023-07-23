package com.cloud.photo.audit.consumer;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.photo.audit.entity.FileAudit;
import com.cloud.photo.audit.service.IFileAuditService;
import com.cloud.photo.common.bo.UserFileBo;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.common.fegin.Cloud2TransService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：kkoneone11
 * @name：AuditConsumer
 * @Date：2023/7/23 10:51
 */

@Component
public class AuditConsumer {

    @Autowired
    IFileAuditService iFileAuditService;
    @Autowired
    Cloud2TransService cloud2TransService;

    @KafkaListener(topics = {"file_audit_topic"})
    public void onMessage(ConsumerRecord<String,Object> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("消费："+record.topic()+"-"+record.partition()+"-"+record.value());
        //组装一下需要的信息
        Object value = record.value();
        JSONObject jsonObject = JSONObject.parseObject(value.toString());
        String userFileId = jsonObject.getString("userFileId");
        String fileMd5 = jsonObject.getString("fileMd5");
        String fileName = jsonObject.getString("fileName");
        Integer fileSize = jsonObject.getInteger("fileSize");
        String storageObjectId = jsonObject.getString("storageObjectId");

        //根据md5查询是否在审核数据库里
        FileAudit fileAudit = iFileAuditService.getOne(new QueryWrapper<FileAudit>().eq("md5", fileMd5), false);

        //1.未审核，不存在数据库中则入库
        if(fileAudit == null){
            //插入审核列表
            FileAudit newFileAudit =new FileAudit();
            newFileAudit.setAuditStatus(CommonConstant.FILE_AUDIT);
            newFileAudit.setFileName(fileName);
            newFileAudit.setMd5(fileMd5);
            newFileAudit.setFileSize(fileSize);
            newFileAudit.setCreateTime(LocalDateTime.now());
            newFileAudit.setUserFileId(userFileId);
            newFileAudit.setStorageObjectId(storageObjectId);
            iFileAuditService.save(newFileAudit);
         //2.已经审核过了
        }else if(fileAudit.getFileAuditId().equals(CommonConstant.FILE_AUDIT_ACCESS)){
         //3.审核过且不通过，则修改状态并更新到数据库中
        }else if(fileAudit.getAuditStatus().equals(CommonConstant.FILE_AUDIT_FAIL)){
            //组装
            UserFileBo userFileBo = new UserFileBo();
            userFileBo.setUserFileId(userFileId);
            userFileBo.setAuditStatus(CommonConstant.FILE_AUDIT_FAIL);

            List<UserFileBo> userFileList = new ArrayList<>();
            userFileList.add(userFileBo);
            cloud2TransService.updateUserFile(userFileList);
        }


    }
}
