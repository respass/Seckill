package scu.zpf.seckill.domain;

import java.util.Date;

public class SeckillGoods {
    private long id;
    private long goodsId;
    private double priece;
    private Date startDate;
    private Date endDate;
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public double getPriece() {
        return priece;
    }

    public void setPriece(double priece) {
        this.priece = priece;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

