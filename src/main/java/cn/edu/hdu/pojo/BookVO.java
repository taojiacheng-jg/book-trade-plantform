package cn.edu.hdu.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookVO {
    private Integer bookId;
    private Integer sellerId;
    private Integer courseId;
    private String title;
    private String isbn;
    private BigDecimal originalPrice;
    private BigDecimal sellingPrice;
    private String conditionDesc;
    private String coverImgPath;
    private String status;
    private LocalDateTime publishTime;
    private String sellerName;
    private BigDecimal creditScore;
    private String courseName;
}
