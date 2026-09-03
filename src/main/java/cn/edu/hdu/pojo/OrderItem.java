package cn.edu.hdu.pojo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private Integer itemId;
    private Integer orderId;
    private Integer bookId;
    private BigDecimal priceSnapshot;
    private Integer quantity;
}
