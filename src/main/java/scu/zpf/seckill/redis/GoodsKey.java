package scu.zpf.seckill.redis;

import scu.zpf.seckill.domain.Goods;

public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(prefix, expireSeconds);
    }

    public static GoodsKey getSeckillGoodsStock = new GoodsKey(0, "stock-");


}
