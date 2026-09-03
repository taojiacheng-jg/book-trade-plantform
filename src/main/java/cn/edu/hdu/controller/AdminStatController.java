package cn.edu.hdu.controller;

import cn.edu.hdu.mapper.AdminMapper;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// 管理员统计看板：正式环境需校验当前用户 role=ADMIN，此处简化未加拦截
@RestController
@RequestMapping("/admin")
public class AdminStatController {

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/stats")
    public Result stats() {
        Map<String, Object> data = new HashMap<>();
        data.put("todayAmount", adminMapper.sumTodayCompletedAmount());
        data.put("totalOrders", adminMapper.countAllOrders());
        data.put("hotBooks", adminMapper.hotBooks());
        data.put("monthlyTrend", adminMapper.monthlyTrend());
        return Result.success(data);
    }
}
