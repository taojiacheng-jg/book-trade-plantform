package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findUserByUsername(@Param("username") String username);
    int insertUser(User user);
}
