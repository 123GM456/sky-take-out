package com.sky.context;

/**
 * 基于 ThreadLocal 的线程上下文工具。
 * 用于在同一线程内传递当前操作用户的 ID（如登录员工 ID），
 * 避免在 Service/Mapper 层层层传递参数。
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 获取当前线程中存储的用户 ID。
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 将当前操作用户 ID 存入线程上下文。
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 移除线程上下文中的数据，防止内存泄漏。
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}