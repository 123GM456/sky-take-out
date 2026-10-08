package com.GM.service.impl;

import com.GM.constant.MessageConstant;
import com.GM.context.BaseContext;
import com.GM.dto.OrdersPageQueryDTO;
import com.GM.dto.OrdersPaymentDTO;
import com.GM.dto.OrdersSubmitDTO;
import com.GM.entity.AddressBook;
import com.GM.entity.OrderDetail;
import com.GM.entity.Orders;
import com.GM.entity.ShoppingCart;
import com.GM.exception.BaseException;
import com.GM.mapper.AddressBookMapper;
import com.GM.mapper.OrderDetailMapper;
import com.GM.mapper.OrderMapper;
import com.GM.mapper.ShoppingCartMapper;
import com.GM.result.PageResult;
import com.GM.service.OrderService;
import com.GM.vo.OrderSubmitVO;
import com.GM.vo.OrderVO;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单业务逻辑实现（Service 层）。
 */
@Service // 标记为 Service 层 Bean，Spring 自动注册
@RequiredArgsConstructor // 为 final 字段生成构造器注入
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final AddressBookMapper addressBookMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    /** 订单状态：待付款 */
    private static final Integer STATUS_PENDING_PAYMENT = 1;
    /** 订单状态：待接单 */
    private static final Integer STATUS_TO_BE_CONFIRMED = 2;
    /** 订单状态：已取消 */
    private static final Integer STATUS_CANCELLED = 6;
    /** 支付状态：未支付 */
    private static final Integer PAY_STATUS_UN_PAID = 0;
    /** 支付状态：已支付 */
    private static final Integer PAY_STATUS_PAID = 1;

    /**
     * 用户下单。
     * @param ordersSubmitDTO 下单参数
     * @return 供前端跳转支付页的信息
     */
    @Override
    @Transactional // 订单主记录、明细、清空购物车必须同一事务，避免只写一半
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文，同时作为归属校验条件
        Long userId = BaseContext.getCurrentId();

        // 1. 校验收货地址：必须存在且属于当前用户（SQL 中已带 user_id 条件）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId(), userId);
        if (addressBook == null) {
            throw new BaseException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        // 2. 查询当前用户购物车，购物车为空不允许下单
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> cartList = shoppingCartMapper.list(shoppingCart);
        if (cartList == null || cartList.isEmpty()) {
            throw new BaseException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 3. 组装订单主记录：客户端字段靠拷贝，服务端生成字段显式设置
        Orders orders = new Orders();
        // 把 DTO 中客户端提供的字段（金额、备注、支付方式、送达时间、餐具等）拷入实体
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        // 订单归属当前登录用户，不接受客户端传入
        orders.setUserId(userId);
        // 订单号用当前时间戳生成，配合 number 唯一索引保证不重复
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        // 新订单固定为待付款、未支付
        orders.setStatus(STATUS_PENDING_PAYMENT);
        orders.setPayStatus(PAY_STATUS_UN_PAID);
        orders.setOrderTime(LocalDateTime.now());
        // 收货信息做快照：下单后再改地址簿不影响已生成的订单
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getProvinceName() + addressBook.getCityName()
                + addressBook.getDistrictName() + addressBook.getDetail());
        orders.setCreateTime(LocalDateTime.now());
        orders.setUpdateTime(LocalDateTime.now());
        // 自增主键由数据库生成并回填到 orders.id，供明细关联使用
        orderMapper.insert(orders);

        // 4. 购物车中每条商品转成一条订单明细
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            // ShoppingCart 与 OrderDetail 的 name/dishId/setmealId/dishFlavor/number/amount/image 同名，可直接拷贝
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        // 5. 下单成功后清空该用户购物车
        shoppingCartMapper.deleteByUserId(userId);

        // 6. 只回传前端跳转支付页所需字段，不暴露整个订单
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
    }

    /**
     * 订单支付。
     * @param ordersPaymentDTO 支付参数
     */
    @Override
    public void payment(OrdersPaymentDTO ordersPaymentDTO) {
        Long userId = BaseContext.getCurrentId();
        // 按订单号 + 用户 ID 定位订单，确保只能支付自己的订单
        Orders orders = orderMapper.getByNumberAndUserId(ordersPaymentDTO.getOrderNumber(), userId);
        if (orders == null) {
            throw new BaseException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 说明：真实项目此处应调用微信支付统一下单接口换取预支付参数再确认支付结果，
        // 本项目未引入微信支付 SDK，此处直接置为支付成功，仅用于打通下单到完成的流程
        Orders updateOrders = new Orders();
        updateOrders.setId(orders.getId());
        updateOrders.setUserId(userId);
        updateOrders.setStatus(STATUS_TO_BE_CONFIRMED);
        updateOrders.setPayStatus(PAY_STATUS_PAID);
        updateOrders.setPayMethod(ordersPaymentDTO.getPayMethod());
        updateOrders.setCheckoutTime(LocalDateTime.now());
        updateOrders.setUpdateTime(LocalDateTime.now());
        orderMapper.update(updateOrders);
    }

    /**
     * 分页查询当前用户的订单。
     * @param ordersPageQueryDTO 分页与状态条件
     * @return 分页结果（含明细）
     */
    @Override
    public PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        // 归属条件由服务端填充，覆盖客户端可能传入的值
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        // 开启分页，PageHelper 拦截紧随其后的第一条 SQL 自动拼接 LIMIT
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<Orders> page = (Page<Orders>) orderMapper.pageQuery(ordersPageQueryDTO);

        // 列表页需要展示订单内商品，逐单补充明细
        List<OrderVO> voList = new ArrayList<>();
        for (Orders orders : page) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orders, orderVO);
            orderVO.setOrderDetailList(orderDetailMapper.getByOrderId(orders.getId()));
            voList.add(orderVO);
        }
        return new PageResult(page.getTotal(), voList);
    }

    /**
     * 按 ID 查询订单详情。
     * @param id 订单 ID
     * @return 订单详情（含明细）
     */
    @Override
    public OrderVO getById(Long id) {
        Long userId = BaseContext.getCurrentId();
        Orders orders = orderMapper.getById(id, userId);
        if (orders == null) {
            throw new BaseException(MessageConstant.ORDER_NOT_FOUND);
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailMapper.getByOrderId(id));
        return orderVO;
    }

    /**
     * 再来一单：把原订单明细重新加入购物车。
     * @param id 订单 ID
     */
    @Override
    @Transactional // 多条明细写入购物车，需同一事务
    public void again(Long id) {
        Long userId = BaseContext.getCurrentId();
        // 先校验订单归属，避免把他人订单的商品加到自己购物车
        Orders orders = orderMapper.getById(id, userId);
        if (orders == null) {
            throw new BaseException(MessageConstant.ORDER_NOT_FOUND);
        }
        for (OrderDetail orderDetail : orderDetailMapper.getByOrderId(id)) {
            ShoppingCart shoppingCart = new ShoppingCart();
            // OrderDetail 的 name/dishId/setmealId/dishFlavor/number/amount/image 与购物车同名，可直接拷贝
            BeanUtils.copyProperties(orderDetail, shoppingCart);
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 取消订单。
     * @param id 订单 ID
     */
    @Override
    public void cancel(Long id) {
        Long userId = BaseContext.getCurrentId();
        Orders orders = orderMapper.getById(id, userId);
        if (orders == null) {
            throw new BaseException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 只有待付款、待接单两种状态允许用户取消，已接单后需联系商家
        if (!STATUS_PENDING_PAYMENT.equals(orders.getStatus())
                && !STATUS_TO_BE_CONFIRMED.equals(orders.getStatus())) {
            throw new BaseException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders updateOrders = new Orders();
        updateOrders.setId(id);
        updateOrders.setUserId(userId);
        updateOrders.setStatus(STATUS_CANCELLED);
        updateOrders.setCancelReason("用户取消");
        updateOrders.setCancelTime(LocalDateTime.now());
        updateOrders.setUpdateTime(LocalDateTime.now());
        orderMapper.update(updateOrders);
    }

    /**
     * 催单。
     * @param id 订单 ID
     */
    @Override
    public void reminder(Long id) {
        Long userId = BaseContext.getCurrentId();
        Orders orders = orderMapper.getById(id, userId);
        if (orders == null) {
            throw new BaseException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 催单本质是把消息推给商家，本项目未引入 WebSocket 消息通道，此处仅记录日志
        log.info("用户催单，订单号：{}", orders.getNumber());
    }
}