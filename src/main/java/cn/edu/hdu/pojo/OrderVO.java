package cn.edu.hdu.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderVO {
    private Integer orderId;
    private Integer buyerId;
    private Integer sellerId;
    private Integer bookId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private String bookTitle;
    private String buyerName;
    private String sellerName;
}
