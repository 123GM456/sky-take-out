package com.GM.mapper;

import com.GM.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 购物车数据访问接口（Mapper / DAO 层）。
 * <p>
 * 复杂 SQL（含动态条件）写在对应的 XML 中。
 * </p>
 */
@Mapper // MyBatis 标记为 Mapper 接口，Spring 自动扫描注册
public interface ShoppingCartMapper {

    /**
     * 条件查询购物车记录。
     * <p>
     * 按非空字段动态拼接条件（userId、dishId、setmealId、dishFlavor），
     * 用于判断某商品是否已在购物车中。
     * </p>
     *
     * @param shoppingCart 查询条件
     * @return 符合条件的购物车记录列表
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 按 ID 更新购物车商品数量。
     *
     * @param shoppingCart 含 id 与最新 number 的记录
     */
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 新增购物车记录。
     *
     * @param shoppingCart 待插入的购物车记录
     */
    void insert(ShoppingCart shoppingCart);

    /**
     * 按 ID 删除购物车记录。
     *
     * @param id 购物车记录 ID
     */
    void deleteById(Long id);

    /**
     * 按用户 ID 清空该用户的购物车记录。
     *
     * @param userId 用户 ID
     */
    void deleteByUserId(Long userId);
}