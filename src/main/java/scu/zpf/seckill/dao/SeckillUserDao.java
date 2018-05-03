package scu.zpf.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import scu.zpf.seckill.domain.SeckillUser;

@Mapper
public interface SeckillUserDao {

    @Select("select * from user where phone = #{phone}")
    SeckillUser getUserByPhone(@Param("phone") String phone);
}
