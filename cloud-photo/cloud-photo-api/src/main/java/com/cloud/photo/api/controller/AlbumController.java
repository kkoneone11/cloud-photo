package com.cloud.photo.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.photo.api.service.AlbumService;
import com.cloud.photo.api.utils.MyUtils;
import com.cloud.photo.common.bo.AlbumPageBo;
import com.cloud.photo.common.bo.FileAnalyzeBo;
import com.cloud.photo.common.bo.FileResizeIconBo;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 相册相关接口
 * @author linzsh
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class AlbumController {


    @Autowired
    AlbumService albumService;

    /**
     * 获取用户照片分类信息
     * @return 带有用户地点、人物、事物 三种标签的缩略图 （三张 按照上传时间排序）
     */
//    @SaCheckLogin
    @GetMapping("/getAlbumCategorize")
    public ResultBody getAlbumCategorize(){
        //缓存中拿到用户信息
        UserBo userInfo = MyUtils.getUserInfo();
        if(userInfo == null){
            return ResultBody.error(CommonEnum.USER_IS_NULL);
        }
        String userId = userInfo.getUserId();
        //组装AlbumPageBo
        AlbumPageBo albumPageBo = new AlbumPageBo();

        albumPageBo.setUserId(userId);
        albumPageBo.setCurrent(1);
        albumPageBo.setPageSize(3);
        //再组装一个jsonObject最后面返回
        JSONObject jsonObject = new JSONObject();
        //人物
        albumPageBo.setCategory(CommonConstant.IMAGE_CATEGORY_1);
        jsonObject.put("person",albumService.getUserAlbumList(albumPageBo));

        //地点
        albumPageBo.setCategory(CommonConstant.IMAGE_CATEGORY_2);
        jsonObject.put("location",albumService.getUserAlbumList(albumPageBo));

        //事物
        albumPageBo.setCategory(CommonConstant.IMAGE_CATEGORY_3);
        jsonObject.put("thing",albumService.getUserAlbumList(albumPageBo));
        return ResultBody.success(jsonObject);
    }

    /**
     * 获取用户所有上传的图片（分页）
     * @return 返回带有文件信息的实体列表（包括大小图、上传时间等）
     */
//    @SaCheckLogin
    @PostMapping("/getUserAlbumList")
    public ResultBody getUserAlbumAll(@RequestBody AlbumPageBo pageBo){
        //从缓存拿到用户信息
        UserBo userInfo = MyUtils.getUserInfo();
        if (userInfo == null){
            return ResultBody.error(CommonEnum.USER_IS_NULL);
        }
        //用户ID
        String userId = userInfo.getUserId();
        pageBo.setUserId(userId);

        // 业务实现
        return albumService.getUserAlbumList(pageBo);
    }

    /**
     * 获取用户单个图片格式分析信息
     * @return 返回带有文件信息的实体列表（包括大小图、上传时间等）
     */
//    @SaCheckLogin
    @PostMapping("/getUserAlbumDetail")
    public ResultBody getUserAlbumDetail(@RequestBody FileAnalyzeBo analyzeBo){
        //从缓存拿到用户信息
        UserBo userInfo = MyUtils.getUserInfo();
        if (userInfo == null){
            return ResultBody.error(CommonEnum.USER_IS_NULL);
        }
        //用户ID
        String userId = userInfo.getUserId();
        analyzeBo.setUserId(userId);
        // 业务实现
        return albumService.getUserAlbumDetail(analyzeBo);
    }

    /**
     * 获取用户单个图片格式分析信息
     * @return 返回带有文件信息的实体列表（包括大小图、上传时间等）
     */
//    @SaCheckLogin
    @GetMapping("/previewImage")
    public ResultBody previewImage(@RequestParam String fileId, @RequestParam String iconCode, HttpServletResponse response){
        FileResizeIconBo fileResizeIconBo =new FileResizeIconBo();
        fileResizeIconBo.setFileId(fileId);
        fileResizeIconBo.setIconCode(iconCode);
        // 业务实现
        ResultBody resultBody = albumService.previewImage(fileResizeIconBo);
        String iconUrl = null;
        if(resultBody.getData()!=null && StringUtils.isNotBlank(resultBody.getData().toString())){
            iconUrl = resultBody.getData().toString();
            response.setStatus(302);
            try {
                response.sendRedirect(iconUrl);
            } catch (IOException e) {
                log.error("response.sendRedirect error!" , e);
            }
        }
        return null;
    }
}
