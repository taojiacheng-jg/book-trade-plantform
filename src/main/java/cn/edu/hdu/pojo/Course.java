package cn.edu.hdu.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Course {
    private Integer courseId;
    private String courseName;
    private String courseCode;
    private String semester;
    private LocalDateTime createdAt;
}
