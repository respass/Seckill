package scu.zpf.seckill.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.service.GoodsService;
import scu.zpf.seckill.vo.GoodsVo;

import java.util.List;

@Controller
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goodsList")
    public String goodsList(Model model, User user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVos);
        return "goodsList";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String goodsDetail(Model model, User user,
                              @PathVariable("goodsId")long goodsId ) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long now = System.currentTimeMillis();
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();


        /**
         * seckill = 0 秒杀还没开始, 1 秒杀已经结束, 2秒杀已经开始
          */
        int status = 0;
        int remainSeconds = 0;
        if (now < startTime){
            status = 0;
            remainSeconds = -1;

        }else if (now > endTime){
            status = 1;
            remainSeconds = (int)(startTime - now) / 1000;
        } else {
            status = 2;
            remainSeconds = 0;

        }
        model.addAttribute("seckillStatus", status);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goodsDetail";

    }
}

