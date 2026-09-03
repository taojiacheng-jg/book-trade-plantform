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
    REGISTER_FAIL(504, "注册失败");

    private final Integer code;
    private final String message;
}
