package scu.zpf.seckill.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.result.Result;
import scu.zpf.seckill.service.UserService;
import scu.zpf.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/do_login")
    public Result<String> login(@Valid LoginVo loginVo, HttpServletResponse response) {
        logger.info(loginVo.toString());
        String token = userService.login(loginVo, response);
        return Result.success(token);

    }




}
