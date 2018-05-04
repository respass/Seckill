package scu.zpf.seckill.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.dao.SeckillUserDao;
import scu.zpf.seckill.domain.SeckillUser;
import scu.zpf.seckill.exception.GlobalException;
import scu.zpf.seckill.redis.RedisService;
import scu.zpf.seckill.redis.SeckillUserKey;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.util.Md5Util;
import scu.zpf.seckill.util.UUIDUtil;
import scu.zpf.seckill.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Service
public class SeckillUserService {

    @Autowired
    SeckillUserDao seckillUserDao;

    @Autowired
    RedisService redisService;

    public static final String COOKIE_NAME_TOKEN = "token";


    public SeckillUser getByPhone(String phone) {
        return seckillUserDao.getUserByPhone(phone);
    }

    public boolean login(LoginVo loginVo, HttpServletResponse response) {
        if (loginVo == null ) {
            throw new GlobalException(CodeMessage.SERVER_ERROR);
        }

        String phone = loginVo.getPhone();
        String formPassword = loginVo.getPassword();

        //验证手机号是否已经注册
        SeckillUser user = getByPhone(phone);
        if (user == null) {
            throw new GlobalException(CodeMessage.PHONE_NOT_REGISTER);
        }

        //验证密码是否正确
        String dbPassword = user.getPassword();
        String saltDb = user.getSalt();
        String password = Md5Util.formPasswordToDbPassword(formPassword, saltDb);
        if (!password.equals(dbPassword)) {
            throw new GlobalException(CodeMessage.PASSWORD_ERROR);
        }


        //登入成功生成Cookie
        String tooken = UUIDUtil.uuid();
        addCookie(response, tooken, user);

        return true;
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {

        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
