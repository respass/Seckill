package scu.zpf.seckill.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import scu.zpf.seckill.domain.SeckillUser;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.result.Result;
import scu.zpf.seckill.service.SeckillUserService;
import scu.zpf.seckill.util.Md5Util;
import scu.zpf.seckill.vo.LoginVo;


@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    SeckillUserService seckillUserService;

    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @PostMapping("/login/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo) {

        logger.info(loginVo.toString());
        seckillUserService.login(loginVo);
        return Result.success(true);
    }


}
