package scu.zpf.seckill.redis;

public abstract class BasePrefix implements KeyPrefix{

    private int expireSecond;
    private String prefix;

    public BasePrefix(String prefix, int expireSeconds) {
        this.expireSecond = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this.prefix = prefix;

        // 默认0, 永不过期
        expireSecond = 0;
    }



    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+ ":" + prefix;
    }


    @Override
    public int expireSeconds() {
        return expireSecond;
    }
}
