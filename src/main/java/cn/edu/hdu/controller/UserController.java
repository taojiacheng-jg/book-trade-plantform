package cn.edu.hdu.controller;

import cn.edu.hdu.service.UserService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @GetMapping("/register")
    public Result register(@RequestParam String username, @RequestParam String password) {
        return userService.register(username, password);
    }
}
