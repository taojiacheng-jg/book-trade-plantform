package cn.edu.hdu.service.impl;

import cn.edu.hdu.mapper.BookMapper;
import cn.edu.hdu.pojo.Book;
import cn.edu.hdu.service.BookService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Value("${book.upload.path:static/upload/books/}")
    private String uploadPath;

    @Override
    public Result publishBook(Book book, MultipartFile coverImage) {
        if (coverImage != null && !coverImage.isEmpty()) {
            String originalFilename = coverImage.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                coverImage.transferTo(new File(dir, fileName));
            } catch (IOException e) {
                return Result.error(cn.edu.hdu.utils.ResultCodeEnum.REGISTER_FAIL);
            }

            book.setCoverImgPath("/upload/books/" + fileName);
        }

        book.setStatus("在售");
        int rows = bookMapper.insertBook(book);
        if (rows > 0) {
            return Result.success(book);
        }
        return Result.error(cn.edu.hdu.utils.ResultCodeEnum.REGISTER_FAIL);
    }

    @Override
    public Result searchBooks(String keyword, Integer courseId, Double minPrice, Double maxPrice,
                              String status, Integer pageNum, Integer pageSize) {
        int page = (pageNum == null || pageNum < 1) ? 1 : pageNum;
        int size = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        int offset = (page - 1) * size;

        List<Book> list = bookMapper.searchBooks(keyword, courseId, minPrice, maxPrice, status, offset, size);
        int total = bookMapper.countSearchBooks(keyword, courseId, minPrice, maxPrice, status);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("list", list);
        data.put("pageNum", page);
        data.put("pageSize", size);
        return Result.success(data);
    }

    @Override
    public Result getRecommendBooks(Integer userId) {
        if (userId == null || userId < 1) {
            return Result.success(java.util.Collections.emptyList());
        }
        List<Integer> courseIds = bookMapper.findCourseIdsByBuyer(userId);
        List<Book> list;
        if (courseIds == null || courseIds.isEmpty()) {
            // 无历史购买 → 推荐最新发布的 10 本在售书
            list = bookMapper.findLatestOnSale(10);
        } else {
            list = bookMapper.recommendBooksByCourseIds(courseIds, 10);
        }
        return Result.success(list);
    }

    @Override
    public Result getPriceReference(String isbn) {
        // 模拟方案：按 ISBN 查书的原价，返回"新书参考价" = 原价 * 1.2（约上浮20%）
        // 实际项目可替换为调用外部API/爬虫（如豆瓣、当当）获取新书实时价，需处理跨域与反爬
        double ref;
        Book book = bookMapper.findByIsbn(isbn);
        if (book != null && book.getOriginalPrice() != null) {
            ref = book.getOriginalPrice().doubleValue() * 1.2;
        } else {
            // 未查到书或无语价时给一个模拟默认参考价
            ref = 59.00;
        }
        BigDecimal referencePrice = BigDecimal.valueOf(ref).setScale(2, RoundingMode.HALF_UP);

        Map<String, Object> data = new HashMap<>();
        data.put("referencePrice", referencePrice);
        return Result.success(data);
    }

    @Override
    public Result listAllBooks() {
        return Result.success(bookMapper.findAllBooks());
    }

    @Override
    public Result forceOffShelf(Integer bookId) {
        int rows = bookMapper.forceOffShelf(bookId);
        if (rows > 0) {
            return Result.success();
        }
        return Result.error(cn.edu.hdu.utils.ResultCodeEnum.BOOK_NOT_FOUND);
    }
}
