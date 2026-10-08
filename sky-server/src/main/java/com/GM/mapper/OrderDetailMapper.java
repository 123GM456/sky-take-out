package com.GM.mapper;

import com.GM.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单明细数据访问接口（Mapper / DAO 层）。
 * <p>
 * 复杂 SQL（含动态条件）写在对应的 XML 中。
 * </p>
 */
@Mapper // MyBatis 标记为 Mapper 接口，Spring 自动扫描注册
public interface OrderDetailMapper {

    /**
     * 批量插入订单明细。
     * <p>一次下单会写入多条明细，用单条 INSERT ... VALUES (...),(...) 减少数据库往返。</p>
     *
     * @param orderDetailList 待插入的明细列表
     */
    void insertBatch(List<OrderDetail> orderDetailList);

    /**
     * 按订单 ID 查询明细列表。
     *
     * @param orderId 订单 ID
     * @return 该订单下的全部商品明细
     */
    List<OrderDetail> getByOrderId(Long orderId);
}