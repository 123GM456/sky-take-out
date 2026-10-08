package com.GM.controller.user;

import com.GM.dto.OrdersPageQueryDTO;
import com.GM.dto.OrdersPaymentDTO;
import com.GM.dto.OrdersSubmitDTO;
import com.GM.entity.Orders;
import com.GM.result.PageResult;
import com.GM.result.Result;
import com.GM.service.OrderService;
import com.GM.vo.OrderSubmitVO;
import com.GM.vo.OrderVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户端 — 订单控制器（Controller 层）。
 * <p>接收微信小程序订单相关请求（下单、支付、查询、取消、催单），调用 Service 处理。</p>
 */
@RestController("userOrderController")  // 指定 Bean 名称，避免与其他端同名 Controller 冲突
@RequestMapping("/user/order")          // 所有方法公用 URL 前缀
@RequiredArgsConstructor                 // 为 final 字段生成构造器注入
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * 用户下单。
     */
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付。
     */
    @PutMapping("/payment")
    public Result payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        log.info("订单支付：{}", ordersPaymentDTO);
        orderService.payment(ordersPaymentDTO);
        return Result.success();
    }

    /**
     * 订单分页查询。
     * <p>「最近订单」与「历史订单」两个入口查询逻辑一致，共用一个方法映射两个路径，
     * 避免在同层出现粒度重复的方法。</p>
     */
    @GetMapping({"/historyOrders", "/userPage"})
    public Result<PageResult> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页查询：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.pageQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情（路径参数传递订单 ID）。
     */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> getById(@PathVariable Long id) {
        log.info("查询订单详情：{}", id);
        OrderVO orderVO = orderService.getById(id);
        return Result.success(orderVO);
    }

    /**
     * 再来一单：把原订单的商品重新加入购物车。
     */
    @PostMapping("/again")
    public Result again(@RequestBody Orders orders) {
        log.info("再来一单：{}", orders.getId());
        orderService.again(orders.getId());
        return Result.success();
    }

    /**
     * 取消订单（路径参数传递订单 ID）。
     */
    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable Long id) {
        log.info("取消订单：{}", id);
        orderService.cancel(id);
        return Result.success();
    }

    /**
     * 催单（路径参数传递订单 ID）。
     */
    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id) {
        log.info("用户催单：{}", id);
        orderService.reminder(id);
        return Result.success();
    }

}