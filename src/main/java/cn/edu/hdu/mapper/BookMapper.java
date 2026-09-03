package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Book;
import cn.edu.hdu.pojo.BookVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    int insertBook(Book book);

    List<BookVO> searchBooks(@Param("keyword") String keyword,
                             @Param("courseId") Integer courseId,
                             @Param("minPrice") Double minPrice,
                             @Param("maxPrice") Double maxPrice,
                             @Param("status") String status,
                             @Param("offset") int offset,
                             @Param("limit") int limit);

    int countSearchBooks(@Param("keyword") String keyword,
                         @Param("courseId") Integer courseId,
                         @Param("minPrice") Double minPrice,
                         @Param("maxPrice") Double maxPrice,
                         @Param("status") String status);

    Book findBookById(Integer bookId);

    BookVO findBookDetail(Integer bookId);

    int lockBookForOrder(@Param("bookId") Integer bookId, @Param("status") String status);

    int updateBookStatus(@Param("bookId") Integer bookId, @Param("status") String status);

    List<Integer> findCourseIdsByBuyer(Integer buyerId);

    List<Book> recommendBooksByCourseIds(@Param("courseIds") List<Integer> courseIds, @Param("limit") int limit);

    List<Book> findLatestOnSale(@Param("limit") int limit);

    Book findByIsbn(String isbn);

    List<BookVO> findAllBooks();

    int forceOffShelf(Integer bookId);

    int offShelfBySeller(@Param("sellerId") Integer sellerId);
}
