package cn.edu.hdu.controller;

import cn.edu.hdu.service.OrderService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result create(@RequestParam Integer buyerId,
                         @RequestParam Integer bookId) {
        return orderService.createOrder(buyerId, bookId);
    }

    @PostMapping("/pay")
    public Result pay(@RequestParam Integer orderId) {
        return orderService.payOrder(orderId);
    }

    @PostMapping("/cancel")
    public Result cancel(@RequestParam Integer orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PostMapping("/confirm")
    public Result confirm(@RequestParam Integer orderId,
                          @RequestParam Integer sellerId) {
        return orderService.confirmReceive(orderId, sellerId);
    }
}
