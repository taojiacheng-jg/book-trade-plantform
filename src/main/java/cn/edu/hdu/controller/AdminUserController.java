package cn.edu.hdu.controller;

import cn.edu.hdu.service.UserService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 管理员权限接口：正式环境需校验当前用户 role=ADMIN，此处简化未加拦截
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Result listUsers() {
        return userService.listUsers();
    }

    @PostMapping("/ban")
    public Result ban(@RequestParam Integer userId) {
        return userService.banUser(userId);
    }

    @PostMapping("/unban")
    public Result unban(@RequestParam Integer userId) {
        return userService.unbanUser(userId);
    }
}
