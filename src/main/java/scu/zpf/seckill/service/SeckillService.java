package scu.zpf.seckill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.vo.GoodsVo;


@Service
public class SeckillService {


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public Order seckill(User user, GoodsVo goodsVo) {

        //减库存,写入SeckillOrder
        goodsService.reduceStock(goodsVo);
        return orderService.createtOrder(user, goodsVo);



    }
}
