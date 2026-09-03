package cn.edu.hdu.service.impl;

import cn.edu.hdu.mapper.BookMapper;
import cn.edu.hdu.mapper.UserMapper;
import cn.edu.hdu.pojo.User;
import cn.edu.hdu.service.UserService;
import cn.edu.hdu.utils.Result;
import cn.edu.hdu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Result login(String username, String password) {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            return Result.error(ResultCodeEnum.USERNAME_ERROR);
        }
        if ("banned".equals(user.getStatus())) {
            return Result.error(ResultCodeEnum.USER_BANNED);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Result.error(ResultCodeEnum.PASSWORD_ERROR);
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result listUsers() {
        List<User> users = userMapper.findAllUsers();
        users.forEach(u -> u.setPassword(null));
        return Result.success(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result banUser(Integer userId) {
        User user = userMapper.findUserById(userId);
        if (user == null) {
            return Result.error(ResultCodeEnum.USER_NOT_FOUND);
        }
        userMapper.updateUserStatus(userId, "banned");
        // 该用户所有"在售"的书一并下架
        bookMapper.offShelfBySeller(userId);
        return Result.success();
    }

    @Override
    public Result unbanUser(Integer userId) {
        User user = userMapper.findUserById(userId);
        if (user == null) {
            return Result.error(ResultCodeEnum.USER_NOT_FOUND);
        }
        userMapper.updateUserStatus(userId, "normal");
        return Result.success();
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
