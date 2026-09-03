package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserMapper {
    User findUserByUsername(@Param("username") String username);
    int insertUser(User user);

    int updateCreditScore(@Param("userId") Integer userId, @Param("creditScore") BigDecimal creditScore);

    List<User> findAllUsers();

    int updateUserStatus(@Param("userId") Integer userId, @Param("status") String status);

    User findUserById(Integer userId);
}
