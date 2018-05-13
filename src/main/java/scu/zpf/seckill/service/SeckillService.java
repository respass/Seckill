package scu.zpf.seckill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.redis.RedisService;
import scu.zpf.seckill.redis.SeckillKey;
import scu.zpf.seckill.vo.GoodsVo;


@Service
public class SeckillService {


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;


    @Transactional
    public Order seckill(User user, GoodsVo goods) {

        //减库存,写入SeckillOrder
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            return orderService.createtOrder(user, goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }

    }

    public long getSeckillResult(long userId, long goodsId){
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        if (seckillOrder != null) {
            return seckillOrder.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
    }
}
