package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> findAll();
}
