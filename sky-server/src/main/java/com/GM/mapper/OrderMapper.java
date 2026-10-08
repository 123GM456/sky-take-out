package com.GM.mapper;

import com.GM.dto.OrdersPageQueryDTO;
import com.GM.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单数据访问接口（Mapper / DAO 层）。
 * <p>
 * 复杂 SQL（含动态条件）写在对应的 XML 中。
 * </p>
 */
@Mapper // MyBatis 标记为 Mapper 接口，Spring 自动扫描注册
public interface OrderMapper {

    /**
     * 新增订单。
     * <p>使用 useGeneratedKeys 将数据库生成的自增主键回填到入参对象的 id 字段。</p>
     *
     * @param orders 待插入的订单记录
     */
    void insert(Orders orders);

    /**
     * 按 ID 查询订单（带归属校验）。
     *
     * @param id     订单 ID
     * @param userId 当前登录用户 ID，确保只能查到自己的订单
     * @return 订单实体，不存在或不属于该用户时返回 null
     */
    Orders getById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 按订单号和用户 ID 查询订单（带归属校验）。
     * <p>用于支付、取消等操作前确认订单确实属于当前登录用户。</p>
     *
     * @param number 订单号
     * @param userId 当前登录用户 ID
     * @return 订单实体，不存在或不属于该用户时返回 null
     */
    Orders getByNumberAndUserId(@Param("number") String number, @Param("userId") Long userId);

    /**
     * 分页查询订单（带 userId 归属校验）。
     * <p>分页由 PageHelper 拦截 SQL 自动完成，此处只负责条件拼装。</p>
     *
     * @param ordersPageQueryDTO 含 page、pageSize、status、userId 的查询条件
     * @return 当前页订单列表
     */
    List<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 修改订单（XML 中使用 &lt;set&gt; 动态 SQL，只更新非空字段）。
     * <p>WHERE 带 user_id 归属校验，防止越权修改他人订单。</p>
     *
     * @param orders 含 id、userId 与待修改字段的订单记录
     */
    void update(Orders orders);
}