package cn.edu.hdu.service.impl;

import cn.edu.hdu.mapper.BookMapper;
import cn.edu.hdu.mapper.MessageMapper;
import cn.edu.hdu.mapper.OrderItemMapper;
import cn.edu.hdu.mapper.OrderMapper;
import cn.edu.hdu.pojo.Book;
import cn.edu.hdu.pojo.Message;
import cn.edu.hdu.pojo.Order;
import cn.edu.hdu.pojo.OrderItem;
import cn.edu.hdu.pojo.OrderVO;
import cn.edu.hdu.service.OrderService;
import cn.edu.hdu.utils.Result;
import cn.edu.hdu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createOrder(Integer buyerId, Integer bookId) {
        Book book = bookMapper.findBookById(bookId);
        if (book == null) {
            return Result.error(ResultCodeEnum.BOOK_NOT_FOUND);
        }
        if (!"在售".equals(book.getStatus())) {
            return Result.error(ResultCodeEnum.BOOK_NOT_ONSALE);
        }

        // 乐观锁：仅当书籍仍为"在售"时才更新为"已预定"，影响行数为0说明已被别人抢先预订
        int locked = bookMapper.lockBookForOrder(bookId, "在售");
        if (locked == 0) {
            return Result.error(ResultCodeEnum.BOOK_LOCKED);
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setSellerId(book.getSellerId());
        order.setTotalAmount(book.getSellingPrice());
        order.setStatus("待付款");
        orderMapper.insertOrder(order);

        OrderItem item = new OrderItem();
        item.setOrderId(order.getOrderId());
        item.setBookId(bookId);
        item.setPriceSnapshot(book.getSellingPrice());
        item.setQuantity(1);
        orderItemMapper.insertOrderItem(item);

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", order.getOrderId());
        return Result.success(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result payOrder(Integer orderId) {
        Order order = orderMapper.findOrderById(orderId);
        if (order == null) {
            return Result.error(ResultCodeEnum.ORDER_NOT_FOUND);
        }
        if (!"待付款".equals(order.getStatus())) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }

        orderMapper.updateOrderStatus(orderId, "已付款");
        orderMapper.updatePayTime(orderId);

        Message msg = new Message();
        msg.setFromUserId(order.getBuyerId());
        msg.setToUserId(order.getSellerId());
        msg.setContent("您的订单已付款，请尽快发货");
        msg.setIsRead(false);
        messageMapper.insertMessage(msg);

        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result cancelOrder(Integer orderId) {
        Order order = orderMapper.findOrderById(orderId);
        if (order == null) {
            return Result.error(ResultCodeEnum.ORDER_NOT_FOUND);
        }
        if (!"待付款".equals(order.getStatus())) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }

        orderMapper.updateOrderStatus(orderId, "已取消");

        // 恢复书籍状态为"在售"
        OrderItem item = orderItemMapper.findByOrderId(orderId);
        if (item != null) {
            bookMapper.updateBookStatus(item.getBookId(), "在售");
        }

        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result confirmReceive(Integer orderId, Integer sellerId) {
        Order order = orderMapper.findOrderById(orderId);
        if (order == null) {
            return Result.error(ResultCodeEnum.ORDER_NOT_FOUND);
        }
        if (!"已付款".equals(order.getStatus())) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
        if (!order.getSellerId().equals(sellerId)) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }

        orderMapper.updateOrderStatus(orderId, "已完成");

        OrderItem item = orderItemMapper.findByOrderId(orderId);
        if (item != null) {
            bookMapper.updateBookStatus(item.getBookId(), "已售出");
        }

        return Result.success();
    }

    @Override
    public Result listOrders(Integer buyerId, Integer sellerId) {
        Object list;
        if (sellerId != null) {
            list = orderMapper.listSellerOrders(sellerId);
        } else if (buyerId != null) {
            list = orderMapper.listBuyerOrders(buyerId);
        } else {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.success(list);
    }

    @Override
    public Result getOrderDetail(Integer orderId) {
        OrderVO detail = orderMapper.findOrderDetail(orderId);
        if (detail == null) {
            return Result.error(ResultCodeEnum.ORDER_NOT_FOUND);
        }
        return Result.success(detail);
    }
}
