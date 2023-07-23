package com.cloud.photo.image.utils;

import cn.hutool.core.date.DateUtil;
import com.cloud.photo.image.entity.MediaInfo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

/**
 * 图片格式分析工具
 * @author linzsh
 */
public class PicUtils {

    /**
     * 使用exif解析图片所有信息
     */
    public static MediaInfo analyzePicture(File file){

        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaInfo mediaInfo = new MediaInfo();
        for(Directory directory : metadata.getDirectories()){
            for(Tag tag : directory.getTags()){
                //if (tag.getTagName().equalsIgnoreCase("Data Precision")) mediaInfo.setDataPrecision(tag.getDescription());
                if (tag.getTagName().equalsIgnoreCase("Image Height")) mediaInfo.setHeight(Integer.parseInt(tag.getDescription().replaceAll(" pixels","")));
                if (tag.getTagName().equalsIgnoreCase("Image Width")) mediaInfo.setWidth(Integer.parseInt(tag.getDescription().replaceAll(" pixels","")));
                if (tag.getTagName().equalsIgnoreCase("Date/Time Original")){
                    mediaInfo.setShootingTime(DateUtil.parse(tag.getDescription(), "yyyy:MM:dd HH:mm:ss").toString());
                }
                //if (tag.getTagName().equalsIgnoreCase("GPS Latitude")) mediaInfo.setLatitude(tag.getDescription());
                //if (tag.getTagName().equalsIgnoreCase("GPS Longitude")) gpsVO.setLongitude(tag.getDescription());
            }
        }

        return mediaInfo;
    }
}
