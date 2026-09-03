package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    int insertOrderItem(OrderItem orderItem);

    OrderItem findByOrderId(Integer orderId);
}
