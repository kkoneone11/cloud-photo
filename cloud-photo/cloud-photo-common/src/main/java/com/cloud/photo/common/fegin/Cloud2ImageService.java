package com.cloud.photo.common.fegin;

import com.cloud.photo.common.bo.FileAnalyzeBo;
import com.cloud.photo.common.bo.FileResizeIconBo;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author：kkoneone11
 * @name：Cloud2ImageService
 * @Date：2023/7/16 12:29
 */
@Service
@FeignClient("cloud-photo-image")
public interface Cloud2ImageService {

    /**
     * 获取缩略图地址
     * @param fileResizeIconBo
     * @return
     */
    @RequestMapping("/image/getIconUrl")
    ResultBody getIconUrl(@RequestBody FileResizeIconBo fileResizeIconBo);

    /**
     * 获取文件格式信息
     * @param fileAnalyzeBo
     * @return
     */
    @RequestMapping("/image/getMediaInfo")
    ResultBody getMediaInfo(@RequestBody FileAnalyzeBo fileAnalyzeBo);
}
