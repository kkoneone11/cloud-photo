package com.cloud.photo.trans.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.photo.trans.entity.StorageObject;
import com.cloud.photo.trans.mapper.StorageObjectMapper;
import com.cloud.photo.trans.service.IStorageObjectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源池文件存储信息 服务实现类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-20
 */
@Service
public class StorageObjectServiceImpl extends ServiceImpl<StorageObjectMapper, StorageObject> implements IStorageObjectService {

}
