package cn.edu.hdu.controller;

import cn.edu.hdu.service.BookService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 管理员权限接口：正式环境需校验当前用户 role=ADMIN，此处简化未加拦截
@RestController
@RequestMapping("/admin")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public Result listBooks() {
        return bookService.listAllBooks();
    }

    @PostMapping("/offShelf")
    public Result offShelf(@RequestParam Integer bookId) {
        return bookService.forceOffShelf(bookId);
    }
}
