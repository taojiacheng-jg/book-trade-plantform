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
    public Result publishBook(@RequestPart("book") Book book,
                              @RequestPart(value = "coverImage", required = false) MultipartFile coverImage) {
        book.setSellerId(2);
        return bookService.publishBook(book, coverImage);
    }
}
