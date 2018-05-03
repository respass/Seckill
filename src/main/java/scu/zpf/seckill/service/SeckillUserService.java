package scu.zpf.seckill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.dao.SeckillUserDao;
import scu.zpf.seckill.domain.SeckillUser;
import scu.zpf.seckill.exception.GlobalException;
import scu.zpf.seckill.result.CodeMessage;
import scu.zpf.seckill.util.Md5Util;
import scu.zpf.seckill.vo.LoginVo;


@Service
public class SeckillUserService {

    @Autowired
    SeckillUserDao seckillUserDao;


    public SeckillUser getByPhone(String phone) {
        return seckillUserDao.getUserByPhone(phone);
    }

    public boolean login(LoginVo loginVo) {
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

        return true;


    }

}
