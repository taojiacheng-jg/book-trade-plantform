package cn.edu.hdu.controller;

import cn.edu.hdu.pojo.Book;
import cn.edu.hdu.service.BookService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/publish")
    public Result publishBook(@ModelAttribute Book book,
                              @RequestParam(value = "coverImage", required = false) MultipartFile coverImage) {
        book.setSellerId(2);   // 临时测试：固定卖家为张三；接入登录会话后请删除
        return bookService.publishBook(book, coverImage);
    }
}
