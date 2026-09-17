package com.GM.handler;

import com.GM.constant.MessageConstant;
import com.GM.exception.BaseException;
import com.GM.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（Handler / 切面层）。
 * <p>通过 {@code @RestControllerAdvice} 拦截所有 Controller 抛出的异常，
 * 按异常类型分流处理，统一包装为 Result 格式返回，避免前端收到 500 裸堆栈。</p>
 *
 * <p>处理优先级：子类异常优先于父类。DuplicateKeyException 是 RuntimeException 的子类，
 * 会比 Exception.class 先匹配。</p>
 */
@RestControllerAdvice  // = @ControllerAdvice + @ResponseBody，返回值自动序列化
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常（BaseException 及其子类）。
     * <p>登录失败、账号禁用等场景主动抛此异常，直接透传 message 给前端。</p>
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> handleBaseException(BaseException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 捕获数据库 UNIQUE 约束冲突（DuplicateKeyException）。
     * <p>MySQL 报错格式：{@code Duplicate entry 'xxx' for key 'employee.uk_username'}，
     * 从中提取重复的用户名，提示"xxx 已存在，请重新输入"。</p>
     */
    @ExceptionHandler  // 未指定 value 时，按方法参数类型 DuplicateKeyException 匹配
    public Result<String> exceptionHandler(DuplicateKeyException ex) {
        String message = ex.getMessage();
        log.error("唯一约束冲突：{}", message);
        if (message.contains("Duplicate entry")) {
            // 截取从 "Duplicate entry " 开始的子串，分割后提取重复的值
            String part = message.substring(message.indexOf("Duplicate entry "));
            String[] split = part.split(" ");
            String username = split[2].replace("'", "");
            return Result.error(username + MessageConstant.USERNAME_DUPLICATE);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 兜底异常处理：捕获所有未被上面方法拦截的异常。
     * <p>返回泛化提示，不暴露堆栈、SQL 等内部信息。</p>
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error("服务器繁忙，请稍后重试");
    }
}