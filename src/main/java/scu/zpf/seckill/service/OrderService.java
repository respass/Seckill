package scu.zpf.seckill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scu.zpf.seckill.dao.OrderDao;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.vo.GoodsVo;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;


    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId){
        SeckillOrder seckillOrder = orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        return seckillOrder;
    }


    public long insertOrder(Order order) {
        return  orderDao.insertOrder(order);
    }

    public void insertSeckillOrder(SeckillOrder seckillOrder) {
        orderDao.insertSeckillOrder(seckillOrder);
    }

    @Transactional
    public Order createtOrder(User user, GoodsVo goodsVo) {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setGoodsCount(1);
        order.setGoodsId(goodsVo.getId());
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        order.setStatus(0);
        order.setUserId(user.getId());
        long orderId = insertOrder(order);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(user.getId());
        insertSeckillOrder(seckillOrder);


        return order;

    }


}
