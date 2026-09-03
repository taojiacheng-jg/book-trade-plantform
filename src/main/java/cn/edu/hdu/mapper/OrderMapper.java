package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);

    Order findOrderById(Integer orderId);

    int updateOrderStatus(@Param("orderId") Integer orderId, @Param("status") String status);

    int updatePayTime(@Param("orderId") Integer orderId);
}
