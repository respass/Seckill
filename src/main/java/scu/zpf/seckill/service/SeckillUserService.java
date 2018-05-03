package scu.zpf.seckill.service;

import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scu.zpf.seckill.dao.SeckillUserDao;
import scu.zpf.seckill.domain.SeckillUser;
import scu.zpf.seckill.exception.GlobalException;
import scu.zpf.seckill.result.CodeMessage;
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

        SeckillUser user = getByPhone(phone);

        if (user == null) {
            throw new GlobalException(CodeMessage.)
        }
    }

}
