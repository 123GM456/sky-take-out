package com.GM.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一返回结果封装（Controller 层通用响应体）。
 * <p>所有 Controller 方法统一返回此类型，前端按 code 判断业务成功/失败：</p>
 * <pre>
 * { "code": 1, "msg": "success", "data": ... }    // 成功
 * { "code": 0, "msg": "错误信息",  "data": null }  // 失败
 * </pre>
 *
 * @param <T> data 字段的具体类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 业务状态码：1=成功，0=失败 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 数据载荷（成功时携带业务数据） */
    private T data;

    /** 成功响应（无数据返回）。 */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    /** 成功响应（携带数据）。 */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = "success";
        result.data = data;
        return result;
    }

    /**
     * 失败响应。
     * @param msg 错误提示信息
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}