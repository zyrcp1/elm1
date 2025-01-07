package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.elm.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserMapper extends BaseMapper<User> {

    @Select("select * from user where userId=#{userId} and password=#{password}")
    public User getUserByIdByPass(@Param("userId") String userId,@Param("password") String password);

    @Select("select count(*) from user where userId=#{userId}")
    public int getUserById(@Param("userId") String userId);

    @Insert("insert into user values(#{userId},#{password},#{userName},#{userSex},null,1)")
    public int saveUser(User user);
}
