package com.GM.context;

/**
 * 基于 ThreadLocal 的线程上下文工具。
 * <p>用于在同一线程内传递当前登录用户 ID，避免在 Service 方法参数中层层传递。
 * 拦截器解析 token 后将用户 ID 存入，Service 中通过 {@link #getCurrentId()} 获取。</p>
 *
 * <p>注意：请求结束后必须在 {@code afterCompletion} 中调用 {@link #removeCurrentId()}，
 * 否则 Tomcat 线程池复用时，下个请求会读到上个请求的用户 ID。</p>
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /** 获取当前线程中存储的登录用户 ID。 */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /** 将当前登录用户 ID 存入线程上下文。 */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /** 请求结束后清理线程上下文，防止 Tomcat 线程池复用导致数据污染。 */
    public static void removeCurrentId() {
        threadLocal.remove();
    }
}