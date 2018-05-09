package scu.zpf.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    //设置对象
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String val2Str = beanToString(value);
            if (val2Str == null || val2Str.length() <= 0) {
                return false;
            }
            //给key添加前缀
            String realKey = prefix.getPrefix() + key;

            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, val2Str);
            }else {
                jedis.setex(realKey, seconds, val2Str);
            }
            return true;

        } finally {
            returnToPool(jedis);
        }

    }

    //通过key取得对象
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String value = jedis.get(realKey);
            T res = stringToBean(value, clazz);
            return res;

        } finally {
            returnToPool(jedis);
        }
    }



    private <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return "" + value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return "" + value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
