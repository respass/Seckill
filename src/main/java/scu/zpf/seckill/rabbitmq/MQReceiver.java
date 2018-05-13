package scu.zpf.seckill.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.domain.SeckillMsg;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.redis.RedisService;
import scu.zpf.seckill.service.GoodsService;
import scu.zpf.seckill.service.OrderService;
import scu.zpf.seckill.service.SeckillService;
import scu.zpf.seckill.vo.GoodsVo;

@Service
public class MQReceiver {

    public static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    OrderService orderService;

   @RabbitListener(queues =  MQConfig.SECKILL_QUEUE)
    public void receive(String msg){

       logger.info("receive: " + msg);
       SeckillMsg seckillMsg = RedisService.stringToBean(msg, SeckillMsg.class);
        User user = seckillMsg.getUser();
        long goodsId = seckillMsg.getGoodsId();
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        int stock = goods.getStock();
        if (stock <= 0) {
            return;
        }

        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (seckillOrder != null){
            return;
        }

        seckillService.seckill(user, goods);
    }
}

