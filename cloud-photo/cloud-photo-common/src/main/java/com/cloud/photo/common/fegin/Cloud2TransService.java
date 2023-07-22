package com.cloud.photo.common.fegin;

import com.cloud.photo.common.bo.AlbumPageBo;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.bo.StorageObjectBo;
import com.cloud.photo.common.bo.UserFileBo;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：kkoneone11
 * @name：Image2TransService
 * @Date：2023/7/15 13:20
 */
@Service
@FeignClient("cloud-photo-trans")
public interface Cloud2TransService {

    /**
     * 获得图片下载地址
     * @param containerId
     * @param objectId
     * @return
     */
    @GetMapping("/trans/getDownloadUrl")
    ResultBody getDownloadUrl(@RequestParam(value = "containerId") String containerId,
                              @RequestParam(value = "objectId") String objectId);

    /**
     * 上传文件
     * @param userId
     * @param fileName
     * @param fileMd5
     * @param fileSize
     * @return
     */
    @GetMapping("/trans/getPutUploadUrl")
    ResultBody getPutUploadUrl(@RequestParam(value = "userId",required = false) String userId,
                                @RequestParam(value = "fileName") String fileName,
                                @RequestParam(value = "fileMd5") String fileMd5,
                                @RequestParam(value = "fileSize") Long fileSize);


    /**
     * 更新文件审核状态
     * @param userFileList
     * @return
     */
     @RequestMapping("/trans/updateUserFile")
     Boolean updateUserFile(@RequestBody List<UserFileBo> userFileList);

    /**
     * 提交上传
     * @param bo
     * @return
     */
    @RequestMapping("/trans/commit")
    ResultBody commit(@RequestBody FileUploadBo bo);

    @RequestMapping("/trans/userFilelist")
    ResultBody userFilelist(@RequestBody AlbumPageBo pageBo);

    @RequestMapping("/trans/getUserFileById")
    UserFileBo getUserFileById(@RequestParam("fileId") String fileId);

    @RequestMapping("/trans/getStorageObjectById")
    StorageObjectBo getStorageObjectById(@RequestParam("storageObjectId") String storageObjectId);



}
