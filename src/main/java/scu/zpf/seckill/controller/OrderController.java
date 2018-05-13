package scu.zpf.seckill.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import scu.zpf.seckill.domain.Goods;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.result.Result;
import scu.zpf.seckill.service.GoodsService;
import scu.zpf.seckill.service.OrderService;
import scu.zpf.seckill.service.UserService;
import scu.zpf.seckill.vo.GoodsVo;
import scu.zpf.seckill.vo.OrderVo;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @GetMapping("/detail1")
    public String orderDetail(HttpServletRequest request,
                              @RequestParam("orderId") long orderId, Model model) {
        return "order_detail";
    }


    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderVo> detail(HttpServletRequest request,
                                       @RequestParam("orderId") long orderId, Model model) {

        User user = userService.getByToken(request);
        if (user == null) {
            return Result.error(CodeMessage.SESSION_ERROR);
        }
        model.addAttribute("user", user);

        Order order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMessage.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
        OrderVo orderVo = new OrderVo();
        orderVo.setGoods(goodsVo);
        orderVo.setOrder(order);
        return Result.success(orderVo);
    }
}
