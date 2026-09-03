package cn.edu.hdu.common;

import lombok.Data;

@Data
public class Result {

    private Integer code;
    private String message;
    private Object data;

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        Result r = new Result();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        r.setData(data);
        return r;
    }

    public static Result error(ResultCodeEnum resultCodeEnum) {
        Result r = new Result();
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }
}
