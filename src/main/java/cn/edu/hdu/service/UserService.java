package cn.edu.hdu.service;

import cn.edu.hdu.utils.Result;

public interface UserService {
    Result login(String username, String password);
    Result register(String username, String password);

    Result listUsers();
    Result banUser(Integer userId);
    Result unbanUser(Integer userId);
}
