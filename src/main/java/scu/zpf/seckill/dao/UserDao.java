package scu.zpf.seckill.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import scu.zpf.seckill.domain.User;

@Mapper
public interface UserDao {

    @Select("select * from user where phone = #{phone}")
    User getUserByPhone(@Param("phone") String phone);

}
