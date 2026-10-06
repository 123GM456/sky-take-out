package com.GM.service;

import com.GM.dto.ShoppingCartDTO;
import com.GM.entity.ShoppingCart;

import java.util.List;

/**
 * 购物车业务逻辑接口（Service 层）。
 */
public interface ShoppingCartService {

    /**
     * 查询当前登录用户的购物车列表。
     *
     * @return 当前用户购物车中的所有商品
     */
    List<ShoppingCart> list();

    /**
     * 添加购物车。
     * <p>同一用户、同一商品（及口味）已存在时数量加一，否则新增一条记录。</p>
     *
     * @param shoppingCartDTO 待加入购物车的商品（dishId 与 setmealId 二选一）
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 减少购物车商品数量。
     * <p>若商品数量为 1，则删除该商品记录。</p>
     *
     * @param shoppingCartDTO 待减少数量的商品（dishId 与 setmealId 二选一）
     */
    void reduce(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车（删除所有商品记录）。
     */
    void clear();
}