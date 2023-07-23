package com.cloud.photo.image.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.photo.common.bo.StorageObjectBo;
import com.cloud.photo.common.bo.UserFileBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.common.fegin.Cloud2TransService;
import com.cloud.photo.image.entity.FileResizeIcon;
import com.cloud.photo.image.entity.MediaInfo;
import com.cloud.photo.image.mapper.FileResizeIconMapper;
import com.cloud.photo.image.service.IFileResizeIconService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.photo.image.service.IMediaInfoService;
import com.cloud.photo.image.utils.DownloadFileUtil;
import com.cloud.photo.image.utils.PicUtils;
import com.cloud.photo.image.utils.UploadFileUtil;
import com.cloud.photo.image.utils.VipsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.UUID;

/**
 * <p>
 * 图片缩略图 服务实现类
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-22
 */
@Service
public class FileResizeIconServiceImpl extends ServiceImpl<FileResizeIconMapper, FileResizeIcon> implements IFileResizeIconService {

    @Autowired
    private IFileResizeIconService iFileResizeIconService;

    @Autowired
    private Cloud2TransService cloud2TransService;

    @Autowired
    private IMediaInfoService iMediaInfoService;


    /**
     * 获得缩略图生成地址
     * @param userId
     * @param fileId
     * @param iconCode
     * @return
     */
    @Override
    public String getIconUrl(String userId, String fileId, String iconCode){
        //根据fileId先把userFile拿出来，为了拿到里面的storageObjectId
        UserFileBo userFile = cloud2TransService.getUserFileById(fileId);

        String storageObjectId = userFile.getStorageObjectId();
        String fileName = userFile.getFileName();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        //查看审核状态
        if(userFile.getAuditStatus().equals(CommonConstant.FILE_AUDIT_FAIL)){
            return getAuditFailIconUrl();
        }

        //查询缩略图是否存在
        FileResizeIcon fileResizeIcon = iFileResizeIconService.getFileResizeIcon(storageObjectId, iconCode);
        //不存在则要生成缩略图的时候需要containerId和objectId，所以根据storageObject拿到storageObject取出里面的元素
        StorageObjectBo storageObject = cloud2TransService.getStorageObjectById(storageObjectId);

        String containerId;
        String objectId ;

        //不存在则生成缩略图
        //1.下载原图
        if(fileResizeIcon == null){

            String srcFileName = iFileResizeIconService.downloadImage(storageObject.getContainerId(),storageObject.getObjectId(),suffixName);
            //原图下载失败
            if(StringUtils.isBlank(suffixName)){
                log.error("downloadResult error!");
                return null;
            }
            //生成缩略图
            FileResizeIcon newFileResizeIcon = iFileResizeIconService.imageThumbnailSave(iconCode, suffixName, srcFileName, storageObjectId, fileName);

            //文件为空或者文件生成失败
            if(newFileResizeIcon == null){
                log.error("imageThumbnailSave() error!");
                return null;
            }
            objectId = newFileResizeIcon.getObjectId();
            containerId = newFileResizeIcon.getContainerId();

        }else{
         //存在则直接生成缩略图下载地址
            objectId = fileResizeIcon.getObjectId();
            containerId = fileResizeIcon.getContainerId();
        }
        ResultBody iconUrlResponse = cloud2TransService.getDownloadUrl(containerId, objectId);
        return iconUrlResponse.getData().toString();
    }

    /**
     * 查询缩略图信息
     * @param storageObjectId
     * @param iconCode
     * @return
     */
    @Override
    public FileResizeIcon getFileResizeIcon(String storageObjectId, String iconCode) {
        
        //组装QueryWrapper
        QueryWrapper<FileResizeIcon> queryWrapper = new QueryWrapper<>();
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("storage_object_id",storageObjectId);
        hm.put("icon_code",iconCode);
        queryWrapper.allEq(hm);
        FileResizeIcon result = iFileResizeIconService.getOne(queryWrapper, false);
        if(result == null) return null;
        return result;
    }

    /**
     *从资源池下载原图
     * @param containerId
     * @param objectId
     * @param suffixName 文件后缀名
     * @return
     */
    @Override
    public String downloadImage(String containerId, String objectId, String suffixName) {
        //将文件下载到该地址
        String srcFileDirName = "D:/cloudImage/";
        //获取原图的下载地址
        ResultBody url = cloud2TransService.getDownloadUrl(containerId, objectId);
        //组装文件名
        String srcFileName = srcFileDirName + UUID.randomUUID().toString() + "." + suffixName;
        //根据文件路径创建一个file类
        File dir = new File(srcFileDirName);
        //判断该路径下的文件存不存在，不存在则创建
        if(!dir.exists()){
            dir.mkdir();
        }
        //将文件下载到该文件路径
        Boolean result = DownloadFileUtil.downloadFile(url.getData().toString(), srcFileName);
        if(!result){
            return null;
        }
        //返回文件名
        return srcFileName;
    }

    /**
     * 生成缩略图
     * @param iconCode
     * @param suffixName
     * @param srcFileName
     * @param storageObjectId
     * @param fileName
     * @return
     */
    @Override
    public FileResizeIcon imageThumbnailSave(String iconCode, String suffixName, String srcFileName, String storageObjectId, String fileName) {
        //要去该路径寻找原图
        String srcFileDirName = "D:/cloudImage/";
        //构造缩略图名字
        String iconFileName = srcFileDirName + UUID.randomUUID().toString() + "." + suffixName;
        //获取宽高信息，进行生成缩略图
        int width = Integer.parseInt(iconCode.split("_")[0]);
        int height = Integer.parseInt(iconCode.split("_")[1]);
        VipsUtil.thumbnail(srcFileName,iconFileName,width,height,"70");


        //文件为空或者文件生成失败
        if(StringUtils.isBlank(iconFileName)||!new File(iconFileName).exists()){
            return null;
        }

        //入库
        FileResizeIcon fileResizeIcon = iFileResizeIconService.uploadIcon(null, storageObjectId, iconCode, new File(iconFileName), fileName);
        return fileResizeIcon;
    }


    /**
     * 调用imageThumbnailSave方法分别生成200和600的缩略图并将缩略图信息入库medi_info表
     * @param storageObjectId
     * @param fileName
     */
    @Override
    public void imageThumbnailAndMediaInfo(String storageObjectId, String fileName){
        String iconCode200 = "200_200";
        String iconCode600 = "600_600";
        //查询一下200和600的缩略图是否存在
        FileResizeIcon fileResizeIcon200 = iFileResizeIconService.getFileResizeIcon(storageObjectId, iconCode200);
        FileResizeIcon fileResizeIcon600 = iFileResizeIconService.getFileResizeIcon(storageObjectId, iconCode600);
        MediaInfo mediaInfo = iMediaInfoService.getOne(new QueryWrapper<MediaInfo>().eq("storage_Object_Id", storageObjectId) ,false);

        //如果都不为空则已经存在缩略图
        if(fileResizeIcon200!=null&&fileResizeIcon600!=null&&mediaInfo!=null){
            return ;
        }
        //缩略图存在的话则下载一下
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        StorageObjectBo storageObject = cloud2TransService.getStorageObjectById(storageObjectId);
        String srcFileName = iFileResizeIconService.downloadImage(storageObject.getContainerId(), storageObject.getObjectId(), suffixName);

        if(StringUtils.isBlank(srcFileName)){
            log.error("downloadImage error!");
            return;
        }

        //生成缩略图并保存入库
        if(fileResizeIcon200 == null){
            iFileResizeIconService.imageThumbnailSave(iconCode200,suffixName,srcFileName,storageObjectId,fileName);
        }
        if(fileResizeIcon600 == null){
            iFileResizeIconService.imageThumbnailSave(iconCode600,suffixName,srcFileName,storageObjectId,fileName);
        }
        //使用分析图片并入mediaInfo库
        MediaInfo newmediaInfo = PicUtils.analyzePicture(new File(srcFileName));
        //组装一下mediaInfo
        newmediaInfo.setStorageObjectId(storageObjectId);
        if(StringUtils.isBlank(newmediaInfo.getShootingTime())){
            newmediaInfo.setShootingTime(DateUtil.now());
        }
        iMediaInfoService.save(newmediaInfo);
    }



    /**
     * 上传缩略图并入库
     * @param userId
     * @param storageObjectId
     * @param iconCode
     * @param iconFile
     * @param fileName
     * @return
     */
    @Override
    public  FileResizeIcon uploadIcon(String userId,String storageObjectId ,String iconCode, File iconFile,String fileName){
        //获得上传地址
        ResultBody putUploadUrl = cloud2TransService.getPutUploadUrl(userId, fileName, null, null);
        //转化成JSON格式并把参数提取出来用作上传到资源池和入库
        JSONObject jsonObject = JSONObject.parseObject(putUploadUrl.getData().toString());
        String objectId = jsonObject.getString("objectId");
        String uploadUrl = jsonObject.getString("url");
        String containerId= jsonObject.getString("containerId");

        //上传到资源池
        UploadFileUtil.uploadSinglePart(iconFile,uploadUrl);

        //入库 file_resize_iocn表
        FileResizeIcon fileResizeIcon = new FileResizeIcon(storageObjectId,iconCode,containerId,objectId);
        iFileResizeIconService.save(fileResizeIcon);
        return fileResizeIcon;
    }


    /**
     * 获取审核失败的照片图
     * @return
     */
    @Override
    public String getAuditFailIconUrl() {
        //查询默认图是否存在存储池
        String iconStorageObjectId = CommonConstant.ICON_STORAGE_OBJECT_ID;
        StorageObjectBo iconStorageObject = cloud2TransService.getStorageObjectById(iconStorageObjectId);

        String containerId = "";
        String objectId = "";
        String srcFileName = "";
        //不存在则从文件中获取违规图上传到存储池
        if(iconStorageObject == null){
            File file = null;
            try{
                file = ResourceUtils.getFile("classpath:static/auditFail.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            FileResizeIcon newFileResizeIcon = iFileResizeIconService.uploadIcon(null, iconStorageObjectId, "200_200", file, "auditFail.jpg");
            containerId = newFileResizeIcon.getContainerId();
            objectId = newFileResizeIcon.getObjectId();
        }else{
            containerId = iconStorageObject.getContainerId();
            objectId = iconStorageObject.getObjectId();
        }
        //生成缩略图下载地址
        ResultBody iconUrlResponse = cloud2TransService.getDownloadUrl(containerId,objectId);
        return iconUrlResponse.getData().toString();
    }





}
