package com.cloud.photo.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


/**
 * @Author：kkoneone11
 * @name：RedisServiceImpl
 * @Date：2023/7/9 15:30
 */
@Service
public class RedisServiceImpl {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String USER_CACHE_PRE = "u_c_";

    /**
     * 设置缓存
     */
    public void setUser2Cache(Long userId,String username){
        //加一个前缀方便数据的对应
        String cacheKey = USER_CACHE_PRE+userId;
        ValueOperations<String,String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(cacheKey,username);
    }

    /**
     * 缓存读取
     */
    public String getUserCache(Long useId){
        String cacheKey = USER_CACHE_PRE + useId;
        ValueOperations<String,String> valueOps = stringRedisTemplate.opsForValue();
        return valueOps.get(cacheKey);
    }
}
