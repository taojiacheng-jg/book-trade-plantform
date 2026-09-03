package cn.edu.hdu.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(200, "操作成功"),
    USERNAME_ERROR(501, "用户名错误"),
    PASSWORD_ERROR(502, "密码错误"),
    USER_ALREADY_EXISTS(503, "用户已存在"),
    REGISTER_FAIL(504, "注册失败"),
    BOOK_NOT_FOUND(505, "书籍不存在"),
    BOOK_NOT_ONSALE(506, "书籍不在售"),
    BOOK_LOCKED(507, "书籍已被预订"),
    ORDER_NOT_FOUND(508, "订单不存在"),
    ORDER_STATUS_ERROR(509, "订单状态不允许该操作"),
    ORDER_CREATE_FAIL(510, "下单失败"),
    ORDER_PAY_FAIL(511, "付款失败"),
    ORDER_CANCEL_FAIL(512, "取消失败");

    private final Integer code;
    private final String message;
}
