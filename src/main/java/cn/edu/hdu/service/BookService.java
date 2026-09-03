package cn.edu.hdu.service;

import cn.edu.hdu.pojo.Book;
import cn.edu.hdu.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Result publishBook(Book book, MultipartFile coverImage);
}
