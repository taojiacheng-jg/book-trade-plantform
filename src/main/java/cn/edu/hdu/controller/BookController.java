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
        // 卖家ID来自请求参数 sellerId；后续可改为从登录会话取当前用户
        return bookService.publishBook(book, coverImage);
    }

    @GetMapping("/search")
    public Result search(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "courseId", required = false) Integer courseId,
                         @RequestParam(value = "minPrice", required = false) Double minPrice,
                         @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                         @RequestParam(value = "status", required = false) String status,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return bookService.searchBooks(keyword, courseId, minPrice, maxPrice, status, pageNum, pageSize);
    }
}
