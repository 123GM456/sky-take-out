package com.GM.exception;

/**
 * 业务异常基类（继承 RuntimeException，由全局异常处理器统一捕获）。
 * <p>所有自定义业务异常都应继承此类，GlobalExceptionHandler 通过一条
 * {@code @ExceptionHandler(BaseException.class)} 统一拦截，避免每个异常写一个处理逻辑。</p>
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    /**
     * @param message 异常描述信息，最终会展示给前端用户
     */
    public BaseException(String message) {
        super(message);
    }
}