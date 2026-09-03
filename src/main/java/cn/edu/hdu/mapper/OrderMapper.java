package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Order;
import cn.edu.hdu.pojo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);

    Order findOrderById(Integer orderId);

    int updateOrderStatus(@Param("orderId") Integer orderId, @Param("status") String status);

    int updatePayTime(@Param("orderId") Integer orderId);

    List<OrderVO> listBuyerOrders(Integer buyerId);

    List<OrderVO> listSellerOrders(Integer sellerId);

    OrderVO findOrderDetail(Integer orderId);
}
