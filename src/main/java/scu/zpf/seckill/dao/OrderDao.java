package scu.zpf.seckill.dao;


import org.apache.ibatis.annotations.*;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.SeckillOrder;

@Mapper
public interface OrderDao {


    @Select("select * from seckillOrder where user_id = #{userId} and goods_id = #{goodsId}")
    SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);


    @Insert("insert into seckill.order(user_id, goods_id, goods_name, goods_count, goods_price, status, create_time)" +
            " values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{status}, #{createTime})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insertOrder(Order order);

    @Insert("insert into seckill.seckillOrder(user_id, order_id, goods_id) values(#{userId}, #{orderId}, #{goodsId})")
    void insertSeckillOrder(SeckillOrder seckillOrder);

    @Select("select * from seckill.order where id = #{orderId}")
    Order getOrderById(@Param("orderId") long orderId);



}
