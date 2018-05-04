package scu.zpf.seckill.redis;



public class SeckillUserKey extends BasePrefix {

    private SeckillUserKey(int expireSeconds, String prefix) {
        super(prefix, expireSeconds);
    }

    //cookie过期时间7天
    public static  final int TOKEN_EXPIRE = 24 * 3600 * 7;

    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE, "tk-" );


}
