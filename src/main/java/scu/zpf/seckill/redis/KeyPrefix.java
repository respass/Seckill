package scu.zpf.seckill.redis;

public interface KeyPrefix {

    String getPrefix();

    int expireSeconds();


}
