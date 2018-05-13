package scu.zpf.seckill.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.domain.SeckillMsg;
import scu.zpf.seckill.redis.RedisService;

@Service
public class MQSender {

    public static Logger logger = LoggerFactory.getLogger(MQSender.class);


    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(SeckillMsg message) {
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg );
        logger.info("send :" + msg);
    }


}
