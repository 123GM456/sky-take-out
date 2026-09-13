package com.GM.result;

// Lombok：自动生成 getter/setter、toString、equals、hashCode（保持 code/msg/data 的读写能力）
import lombok.Data;
// JDK：实现 Serializable 接口，使 Result 对象可序列化（Redis 缓存 / 网络传输 / 序列化存储）
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
// Lombok：自动生成 getter/setter、toString、equals、hashCode、canEqual
@Data
// 统一响应封装：所有 Controller 方法统一返回此类型，前端按 code 判断成功/失败
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