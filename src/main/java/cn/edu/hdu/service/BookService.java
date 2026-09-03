package cn.edu.hdu.service;

import cn.edu.hdu.pojo.Book;
import cn.edu.hdu.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Result publishBook(Book book, MultipartFile coverImage);

    Result searchBooks(String keyword, Integer courseId, Double minPrice, Double maxPrice,
                       String status, Integer pageNum, Integer pageSize);

    Result getRecommendBooks(Integer userId);

    Result getPriceReference(String isbn);

    Result listAllBooks();
    Result forceOffShelf(Integer bookId);
}
