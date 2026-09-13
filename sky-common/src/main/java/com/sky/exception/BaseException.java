package com.sky.exception;

/**
 * 业务异常的基类（继承 RuntimeException，由全局异常处理器统一捕获）。
 * <p>所有自定义业务异常（如 LoginFailedException）都应继承此类，
 * 保证 GlobalExceptionHandler 能用一条 @ExceptionHandler 统一处理。</p>
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

}