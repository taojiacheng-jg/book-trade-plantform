package cn.edu.hdu.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    USERNAME_ERROR(501, "用户名不存在"),
    PASSWORD_ERROR(502, "密码错误"),
    USER_ALREADY_EXISTS(503, "用户已存在"),
    REGISTER_FAIL(504, "注册失败"),
    PARAM_ERROR(505, "参数错误");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
