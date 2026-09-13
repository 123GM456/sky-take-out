package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（@RestControllerAdvice）。
 * <p>捕获 Controller 层抛出的所有异常，按类型分流处理，
 * 统一包装为 Result 格式返回，避免前端收到 500 裸堆栈。</p>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常（BaseException 及其子类）。
     * 如：LoginFailedException、AccountNotFoundException、PasswordErrorException。
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> handleBaseException(BaseException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 兜底异常处理：捕获所有未被上面方法拦截的异常。
     * 返回泛化提示，不暴露内部实现细节。
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error("服务器繁忙，请稍后重试");
    }

}