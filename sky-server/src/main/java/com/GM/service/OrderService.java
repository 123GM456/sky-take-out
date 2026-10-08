package com.GM.service;

import com.GM.dto.OrdersPageQueryDTO;
import com.GM.dto.OrdersPaymentDTO;
import com.GM.dto.OrdersSubmitDTO;
import com.GM.result.PageResult;
import com.GM.vo.OrderSubmitVO;
import com.GM.vo.OrderVO;

/**
 * 订单业务逻辑接口（Service 层）。
 */
public interface OrderService {

    /**
     * 用户下单。
     * <p>校验收货地址与购物车后生成订单主记录和明细，并清空购物车。</p>
     *
     * @param ordersSubmitDTO 下单参数（地址、备注、送达时间、餐具、金额等）
     * @return 供前端跳转支付页的最小信息（订单 ID、订单号、金额、下单时间）
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付。
     *
     * @param ordersPaymentDTO 支付参数（订单号、支付方式）
     */
    void payment(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 分页查询当前用户的订单。
     *
     * @param ordersPageQueryDTO 分页与状态筛选条件
     * @return 分页结果，每条记录含订单明细
     */
    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 按 ID 查询订单详情。
     *
     * @param id 订单 ID
     * @return 订单详情（含明细列表）
     */
    OrderVO getById(Long id);

    /**
     * 再来一单：把原订单的商品明细重新加入购物车。
     *
     * @param id 订单 ID
     */
    void again(Long id);

    /**
     * 取消订单（仅待付款、待接单状态可取消）。
     *
     * @param id 订单 ID
     */
    void cancel(Long id);

    /**
     * 催单。
     *
     * @param id 订单 ID
     */
    void reminder(Long id);
}