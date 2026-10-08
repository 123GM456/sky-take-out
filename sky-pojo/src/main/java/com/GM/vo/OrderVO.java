package com.GM.vo;

import com.GM.entity.OrderDetail;
import com.GM.entity.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单视图对象（VO），用于历史订单列表与订单详情展示。
 * <p>继承订单实体，在订单全部字段之上补充订单明细列表，
 * 以及供前端直接展示的"订单菜品摘要"字符串。</p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderVO extends Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 订单明细列表（一张订单对应多条商品记录） */
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    /** 菜品摘要（如"宫保鸡丁*1;米饭*2"，前端列表页直接展示用） */
    private String orderDishes;
}