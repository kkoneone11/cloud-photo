package com.cloud.photo.api.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.cloud.photo.api.utils.MyUtils;
import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 传输管理相关接口
 * @author kkoneone11
 */
@RestController
@RequestMapping("/api")
public class TransController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/getTransList")
    public ResultBody getTransList(){
        UserBo userInfo = MyUtils.getUserInfo();
        //用户信息为空则返回错误
        if(userInfo == null){
            return ResultBody.error(CommonEnum.USER_IS_NULL);
        }
        //不为空则拿到userId
        String userId = userInfo.getUserId();
        //组装匹配前缀
        String keyPatten = userId + ":" + "*";
        //拿出keys
        Set<String> keys = stringRedisTemplate.keys(keyPatten);
        //keys不为空则遍历
        if(keys!=null && CollUtil.isNotEmpty(keys)){
            List<FileUploadBo> uploadBos = keys.stream().map(key -> {
                String value = stringRedisTemplate.opsForValue().get(key);
                return JSONUtil.toBean(value,FileUploadBo.class);
            }).collect(Collectors.toList());
            //按上传时间降序
            uploadBos = uploadBos.stream().sorted(Comparator.comparing(FileUploadBo::getUploadTime, Comparator.reverseOrder())).collect(Collectors.toList());
            return ResultBody.success(uploadBos);
        }else{
            return ResultBody.error(CommonEnum.TRANS_IS_NULL);
        }

    }
}
