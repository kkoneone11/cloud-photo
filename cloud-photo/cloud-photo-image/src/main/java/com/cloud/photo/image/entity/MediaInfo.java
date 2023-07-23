package com.cloud.photo.image.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 文件媒体信息
 * </p>
 *
 * @author kkoneone11
 * @since 2023-07-22
 */
@TableName("tb_media_info")
public class MediaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String mediaInfoId;

    private Integer width;

    private Integer height;

    /**
     * 拍摄时间
     */
    private String shootingTime;

    private Integer gpsLatitude;

    private Integer gpsLongitude;

    private String storageObjectId;

    public String getMediaInfoId() {
        return mediaInfoId;
    }

    public void setMediaInfoId(String mediaInfoId) {
        this.mediaInfoId = mediaInfoId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getShootingTime() {
        return shootingTime;
    }

    public void setShootingTime(String shootingTime) {
        this.shootingTime = shootingTime;
    }

    public Integer getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Integer gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Integer getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(Integer gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getStorageObjectId() {
        return storageObjectId;
    }

    public void setStorageObjectId(String storageObjectId) {
        this.storageObjectId = storageObjectId;
    }

    @Override
    public String toString() {
        return "MediaInfo{" +
            "mediaInfoId = " + mediaInfoId +
            ", width = " + width +
            ", height = " + height +
            ", shootingTime = " + shootingTime +
            ", gpsLatitude = " + gpsLatitude +
            ", gpsLongitude = " + gpsLongitude +
            ", storageObjectId = " + storageObjectId +
        "}";
    }
}
