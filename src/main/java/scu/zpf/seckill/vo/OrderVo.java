package scu.zpf.seckill.vo;

import scu.zpf.seckill.domain.Order;

public class OrderVo {
    private GoodsVo goods;
    private Order order;
    public GoodsVo getGoods() {
        return goods;
    }
    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
