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
}
