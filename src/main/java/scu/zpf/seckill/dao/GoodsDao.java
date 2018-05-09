package scu.zpf.seckill.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import scu.zpf.seckill.vo.GoodsVo;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*, sg.stock, sg.start_date, sg.end_date, sg.price as seckillPrice from seckillGoods sg left join goods g on sg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*, sg.stock, sg.start_date, sg.end_date, sg.price as seckillPrice from seckillGoods sg left join goods g on sg.goods_id = g.id " +
            "where g.id = #{goodsId}")
    GoodsVo getGoodsByGoodsId(@Param("goodsId") long goodsId);

}
