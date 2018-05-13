package scu.zpf.seckill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.dao.GoodsDao;
import scu.zpf.seckill.domain.SeckillGoods;
import scu.zpf.seckill.vo.GoodsVo;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsByGoodsId(long goodsId) {
        return goodsDao.getGoodsByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(goods);
        return ret > 0;
    }

}
