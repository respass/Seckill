package scu.zpf.seckill.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.dao.UserDao;
import scu.zpf.seckill.domain.SeckillOrder;
import scu.zpf.seckill.domain.User;
import scu.zpf.seckill.exception.GlobalException;
import scu.zpf.seckill.redis.RedisService;
import scu.zpf.seckill.redis.UserKey;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.util.CookieUtil;
import scu.zpf.seckill.util.Md5Util;
import scu.zpf.seckill.util.UUIDUtil;
import scu.zpf.seckill.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserService {

    @Autowired
    UserDao userDao;


    @Autowired
    RedisService redisService;

    public static final String COOKIE_NAME_TOKEN = "token";


    public User getByPhone(String phone) {

        //1.从缓存中取
        User user = redisService.get(UserKey.getByPhone, phone, User.class );
        if (user != null){
            return user;
        }

        //2.从数据库中取
        user = userDao.getUserByPhone(phone);

        //3.写入缓存
        if (user != null){
            redisService.set(UserKey.getByPhone, phone, user);
        }
        return user;
    }

    public String login(LoginVo loginVo, HttpServletResponse response) {
        if (loginVo == null ) {
            throw new GlobalException(CodeMessage.SERVER_ERROR);
        }

        String phone = loginVo.getPhone();
        String formPassword = loginVo.getPassword();

        //验证手机号是否已经注册
        User user = getByPhone(phone);
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


        //生成token
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);

        return token;
    }


    public User getByToken(HttpServletRequest request) {

        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, UserService.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        User user = redisService.get(UserKey.token, token, User.class);
        return user;
    }

    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {

        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }







}
