package com.cloud.photo.trans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.trans.entity.UserFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-20
 */
public interface IUserFileService extends IService<UserFile> {

    /**
     * 根据fileUploadBo构造的一个save方法，并用来对Kafka发送消息
     * @param fileUploadBo
     * @return
     */
    boolean saveAndFileDeal(FileUploadBo fileUploadBo);

}
