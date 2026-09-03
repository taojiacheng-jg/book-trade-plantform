package cn.edu.hdu.service.impl;

import cn.edu.hdu.mapper.UserMapper;
import cn.edu.hdu.pojo.User;
import cn.edu.hdu.service.UserService;
import cn.edu.hdu.utils.Result;
import cn.edu.hdu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Result login(String username, String password) {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            return Result.error(ResultCodeEnum.USERNAME_ERROR);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Result.error(ResultCodeEnum.PASSWORD_ERROR);
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result register(String username, String password) {
        User existing = userMapper.findUserByUsername(username);
        if (existing != null) {
            return Result.error(ResultCodeEnum.USER_ALREADY_EXISTS);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        int rows = userMapper.insertUser(user);
        if (rows > 0) {
            return Result.success();
        }
        return Result.error(ResultCodeEnum.REGISTER_FAIL);
    }
}
