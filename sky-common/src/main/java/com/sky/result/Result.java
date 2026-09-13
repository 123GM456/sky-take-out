package com.sky.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一返回结果封装。
 * 所有 Controller 接口统一使用此类包装响应数据，前端统一解析格式：
 * <pre>
 * {
 *   "code": 1,       // 1=成功，0=失败
 *   "msg": "...",    // 提示信息
 *   "data": {}       // 数据载荷
 * }
 * </pre>
 *
 * @param <T> 数据载荷的类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 业务状态码：1=成功，0=失败 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 数据载荷 */
    private T data;

    /**
     * 成功响应（无数据返回）。
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    /**
     * 成功响应（携带数据）。
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = "success";
        result.data = data;
        return result;
    }

    /**
     * 失败响应。
     *
     * @param msg 错误提示信息
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }

}