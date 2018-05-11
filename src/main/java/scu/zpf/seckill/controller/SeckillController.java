package scu.zpf.seckill.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.service.GoodsService;
import scu.zpf.seckill.service.OrderService;
import scu.zpf.seckill.service.SeckillService;
import scu.zpf.seckill.service.UserService;
import scu.zpf.seckill.vo.GoodsVo;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SeckillController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    @PostMapping("/goods/seckill")
    public String seckill(HttpServletRequest request, @RequestParam("goodsId") long goodsId, Model model) {


        User user = userService.getByToken(request);

        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);


        //查看库存
        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
        int stock = goodsVo.getStock();
        if (stock <= 0) {
            model.addAttribute("error_msg", CodeMessage.Seckill_END);
            return "fail";
        }



        //是否已经秒杀到了
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (seckillOrder != null) {
            model.addAttribute("error_msg", CodeMessage.Seckill_Repeat);
            return "fail";
        }


        //减库存, 下订单, 写入SeckillOrder
        Order order = seckillService.seckill(user, goodsVo);
        model.addAttribute("goods", goodsVo);
        model.addAttribute("order", order);


        return "orderDetail";
    }






}
