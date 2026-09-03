package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    int insertBook(Book book);
}
