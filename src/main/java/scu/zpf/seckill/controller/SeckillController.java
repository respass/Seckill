package scu.zpf.seckill.controller;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import scu.zpf.seckill.domain.Order;
import scu.zpf.seckill.domain.SeckillMsg;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.rabbitmq.MQSender;
import scu.zpf.seckill.redis.GoodsKey;
import scu.zpf.seckill.redis.RedisService;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.result.Result;
import scu.zpf.seckill.service.GoodsService;
import scu.zpf.seckill.service.OrderService;
import scu.zpf.seckill.service.SeckillService;
import scu.zpf.seckill.service.UserService;
import scu.zpf.seckill.vo.GoodsVo;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class SeckillController implements InitializingBean {

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    MQSender mqSender;

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();


    //服务启动时缓存可秒杀的商品
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods :
                goodsList) {
            redisService.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(), goods.getStock());
            localOverMap.put(goods.getId(), false);
        }
    }

    @PostMapping("/seckill")
    @ResponseBody
    public Result<Integer> seckill(HttpServletRequest request,
                                   @RequestParam("goodsId") Long goodsId, Model model) {



        User user = userService.getByToken(request);

        if (user == null) {
            return Result.error(CodeMessage.SESSION_ERROR);
        }
        model.addAttribute("user", user);

        //Long goodsId = Long.valueOf(goodsIdStr);

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMessage.Seckill_END);
        }

        //redis预减库存
        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMessage.Seckill_END);
        }

        //是否已经秒杀过了
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (seckillOrder != null) {
            return Result.error(CodeMessage.Seckill_Repeat);
        }

        //入队
        SeckillMsg msg = new SeckillMsg();
        msg.setGoodsId(goodsId);
        msg.setUser(user);
        mqSender.send(msg);



        return Result.success(0); //排队中





//        //查看库存
//        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
//        int stock = goodsVo.getStock();
//        if (stock <= 0) {
//            model.addAttribute("error_msg", CodeMessage.Seckill_END);
//            return "fail";
//        }
//
//
//
//        //是否已经秒杀到了
//        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (seckillOrder != null) {
//            model.addAttribute("error_msg", CodeMessage.Seckill_Repeat);
//            return "fail";
//        }
//
//
//        //减库存, 下订单, 写入SeckillOrder
//        Order order = seckillService.seckill(user, goodsVo);
//        model.addAttribute("goods", goodsVo);
//        model.addAttribute("order", order);


//        return "orderDetail";
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @GetMapping("/seckill/result")
    @ResponseBody
    public Result<Long> seckillResult(Model model, HttpServletRequest request,
                                      @RequestParam("goodsId") Long goodsId) {

        User user = userService.getByToken(request);

        if (user == null) {
            return Result.error(CodeMessage.SESSION_ERROR);
        }

        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMessage.SESSION_ERROR);
        }

        //Long goodsId = Long.valueOf(goodsIdStr);
        Long result = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);

    }








}
