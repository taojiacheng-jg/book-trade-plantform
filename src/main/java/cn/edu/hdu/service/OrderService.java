package cn.edu.hdu.service;

import cn.edu.hdu.utils.Result;

public interface OrderService {
    Result createOrder(Integer buyerId, Integer bookId);

    Result payOrder(Integer orderId);

    Result cancelOrder(Integer orderId);

    Result confirmReceive(Integer orderId, Integer sellerId);

    Result listOrders(Integer buyerId, Integer sellerId);

    Result getOrderDetail(Integer orderId);
}
