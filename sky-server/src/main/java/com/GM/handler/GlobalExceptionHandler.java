package com.GM.handler;

// 项目异常：自定义业务异常的基类，所有业务异常都应继承它
import com.GM.exception.BaseException;
// 项目工具：统一响应封装，将异常信息转化为标准 Result 格式返回
import com.GM.result.Result;
// 项目常量：USERNAME_DUPLICATE 等业务提示文案
import com.GM.constant.MessageConstant;
// Lombok：为当前类生成 log 日志对象
import lombok.extern.slf4j.Slf4j;
// Spring DAO：数据库 UNIQUE 约束冲突时 Spring JDBC 抛出的异常（包装了底层 SQLException）
import org.springframework.dao.DuplicateKeyException;
// Spring MVC：声明方法处理特定异常的注解
import org.springframework.web.bind.annotation.ExceptionHandler;
// Spring MVC：定义全局异常处理类的注解（组合了 @ControllerAdvice + @ResponseBody）
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（@RestControllerAdvice）。
 * <p>捕获 Controller 层抛出的所有异常，按类型分流处理，
 * 统一包装为 Result 格式返回，避免前端收到 500 裸堆栈。</p>
 * <p>处理优先级：子类异常匹配优先于父类，所以 DuplicateKeyException（RuntimeException 子类）
 * 会先于 Exception.class 被捕获。</p>
 */
// Spring MVC：拦截所有 Controller 抛出的异常，返回值自动写入 HTTP 响应体
@RestControllerAdvice
// Lombok：自动生成 private static final Logger log = LoggerFactory.getLogger(...)
@Slf4j
// 全局异常处理器：统一捕获 Controller 层抛出的异常，按类型分流处理后返回 Result 格式
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常（BaseException 及其子类）。
     * 登录失败、账号禁用等场景主动抛此类异常，Handler 直接透传 message 给前端。
     */
    // Spring MVC：声明当前方法处理 BaseException 类型的异常
    @ExceptionHandler(BaseException.class)
    public Result<String> handleBaseException(BaseException e) {
        // warn 级别：业务异常是预期内的错误，不影响主流程
        log.warn("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 捕获数据库 UNIQUE 约束冲突（DuplicateKeyException）。
     * <p>Spring JDBC 会把底层的 SQLIntegrityConstraintViolationException
     * 自动转换为 DuplicateKeyException 再抛出，所以这里能直接捕获到。</p>
     * <p>MySQL 报错格式："Duplicate entry 'admin' for key 'employee.uk_username'"</p>
     */
    // Spring MVC：注解未指定 value 时，默认按方法参数类型（DuplicateKeyException）匹配
    @ExceptionHandler
    public Result<String> exceptionHandler(DuplicateKeyException ex) {
        String message = ex.getMessage();
        log.error("唯一约束冲突：{}", message);
        // 只处理包含 "Duplicate entry" 的情况，其他约束异常（如外键）走兜底
        if (message.contains("Duplicate entry")) {
            // 截取从 "Duplicate entry " 开始的子串，去掉前面 JDBC 驱动的前缀
            String part = message.substring(message.indexOf("Duplicate entry "));
            // 按空格分割：["Duplicate", "entry", "'admin'", "for", "key", ...]
            String[] split = part.split(" ");
            // split[2] 是带单引号的 'admin'，去掉首尾引号得到纯净的重复值
            String username = split[2].replace("'", "");
            String msg = username + MessageConstant.USERNAME_DUPLICATE;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 兜底异常处理：捕获所有未被上面方法拦截的异常。
     * 返回泛化提示，不暴露内部实现细节（比如堆栈、SQL 语句等敏感信息）。
     */
    // Spring MVC：捕获 Exception 类型的异常（范围最广，作为最后一道防线）
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        // error 级别：未知异常需要开发人员介入排查，记录完整堆栈
        log.error("系统异常：", e);
        return Result.error("服务器繁忙，请稍后重试");
    }

}