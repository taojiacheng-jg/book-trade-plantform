package cn.edu.hdu.controller;

import cn.edu.hdu.mapper.CourseMapper;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/list")
    public Result list() {
        return Result.success(courseMapper.findAll());
    }
}
