package com.cloud.photo.api.service;

import com.cloud.photo.common.bo.AlbumPageBo;
import com.cloud.photo.common.bo.FileAnalyzeBo;
import com.cloud.photo.common.bo.FileResizeIconBo;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相册相关接口
 * @author linzsh
 */
@RestController
public interface AlbumService {

    ResultBody getUserAlbumList(AlbumPageBo pageBo);

    ResultBody getUserAlbumDetail(FileAnalyzeBo analyzeBo);

    ResultBody previewImage(FileResizeIconBo fileResizeIconBo);
}
