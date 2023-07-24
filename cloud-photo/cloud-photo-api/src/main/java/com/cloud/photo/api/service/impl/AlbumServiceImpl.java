package com.cloud.photo.api.service.impl;


import com.cloud.photo.api.service.AlbumService;
import com.cloud.photo.common.bo.AlbumPageBo;
import com.cloud.photo.common.bo.FileAnalyzeBo;
import com.cloud.photo.common.bo.FileResizeIconBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.fegin.Cloud2ImageService;
import com.cloud.photo.common.fegin.Cloud2TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weifucheng
 */
@Service
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    private Cloud2TransService cloud2TransService;

    @Autowired
    private Cloud2ImageService cloud2ImageService;

    @Override
    public ResultBody getUserAlbumList(AlbumPageBo pageBo) {
        return cloud2TransService.userFilelist(pageBo);
    }

    @Override
    public ResultBody getUserAlbumDetail(FileAnalyzeBo analyzeBo) {
        return cloud2ImageService.getMediaInfo(analyzeBo);
    }

    @Override
    public ResultBody previewImage(FileResizeIconBo fileResizeIconBo) {
        return cloud2ImageService.getIconUrl(fileResizeIconBo);
    }
}
