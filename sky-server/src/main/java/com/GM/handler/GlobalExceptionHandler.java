package com.GM.handler;

// 项目异常：自定义业务异常的基类，所有业务异常都应继承它
import com.GM.exception.BaseException;
// 项目工具：统一响应封装，将异常信息转化为标准 Result 格式返回
import com.GM.result.Result;
// Lombok：为当前类生成 log 日志对象
import lombok.extern.slf4j.Slf4j;
// Spring MVC：声明方法处理特定异常的注解
import org.springframework.web.bind.annotation.ExceptionHandler;
// Spring MVC：定义全局异常处理类的注解（组合了 @ControllerAdvice + @ResponseBody）
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（@RestControllerAdvice）。
 * <p>捕获 Controller 层抛出的所有异常，按类型分流处理，
 * 统一包装为 Result 格式返回，避免前端收到 500 裸堆栈。</p>
 */
// Spring MVC：定义全局异常处理类，返回的数据自动写入 HTTP 响应体
@RestControllerAdvice
// Lombok：生成 log 日志对象
@Slf4j
// 全局异常处理器：统一捕获 Controller 层抛出的异常，按类型分流处理后返回 Result 格式
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常（BaseException 及其子类）。
     * 如：LoginFailedException、AccountNotFoundException、PasswordErrorException。
     */
    // Spring MVC：声明当前方法处理指定类型的异常（@ExceptionHandler + 参数类型匹配）
    @ExceptionHandler(BaseException.class)
    public Result<String> handleBaseException(BaseException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 兜底异常处理：捕获所有未被上面方法拦截的异常。
     * 返回泛化提示，不暴露内部实现细节。
     */
    // Spring MVC：捕获 Exception 类型的异常（范围上比 BaseException 更广，做兜底用）
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error("服务器繁忙，请稍后重试");
    }

}