package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 订单分页查询参数 DTO，接收用户端历史订单/最近订单列表的查询条件。
 */
@Data
public class OrdersPageQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 页码（从 1 开始） */
    private int page;

    /** 每页显示条数 */
    private int pageSize;

    /** 订单状态（可选，传值时按状态筛选：1=待付款，2=待接单……） */
    private Integer status;

    /** 用户 ID（由 Service 层从登录上下文填充，用于归属校验，不接受客户端传入） */
    private Long userId;
}