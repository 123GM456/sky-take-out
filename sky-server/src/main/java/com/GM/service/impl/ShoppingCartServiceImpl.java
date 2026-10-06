package com.GM.service.impl;

import com.GM.context.BaseContext;
import com.GM.dto.ShoppingCartDTO;
import com.GM.entity.ShoppingCart;
import com.GM.mapper.DishMapper;
import com.GM.mapper.SetmealMapper;
import com.GM.mapper.ShoppingCartMapper;
import com.GM.service.ShoppingCartService;
import com.GM.vo.DishVO;
import com.GM.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车业务逻辑实现（Service 层）。
 */
@Service // 标记为 Service 层 Bean，Spring 自动注册
@Slf4j
@RequiredArgsConstructor // 为 final 字段生成构造器注入
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;

    private final DishMapper dishMapper;

    private final SetmealMapper setmealMapper;

    /**
     * 查询当前登录用户的购物车列表。
     */
    @Override
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart = new ShoppingCart();
        // 只查询当前登录用户的购物车记录
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * 添加购物车。
     * <p>
     * 流程：拷贝 DTO 并补全当前用户 ID → 查询该商品是否已在购物车 →
     * 已存在则数量加一，否则根据菜品/套餐补全名称、图片、单价后新增。
     * </p>
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 查询当前用户的该商品是否已在购物车中（含口味区分）
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && !list.isEmpty()) {
            // 已存在：数量加一后更新
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
            return;
        }

        // 不存在：根据菜品或套餐补全商品名称、图片、单价
        if (shoppingCartDTO.getDishId() != null) {
            // 当前加入的是菜品
            DishVO dish = dishMapper.getById(shoppingCartDTO.getDishId());
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        } else {
            // 当前加入的是套餐
            SetmealVO setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setAmount(setmeal.getPrice());
        }
        // 首次加入数量为 1，记录创建时间
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }

    /**
     * 减少购物车商品数量。
     * <p>若商品数量为 1，则删除该商品记录。</p>
     */
    @Override
    public void reduce(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文
        shoppingCart.setUserId(BaseContext.getCurrentId());
        // 查询当前用户的该商品是否已在购物车中（含口味区分）
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && !list.isEmpty()) {
            // 已存在：数量减一后更新
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartMapper.updateNumberById(cart);
            return;
        }
        // 不存在：直接删除
        shoppingCartMapper.deleteById(shoppingCart.getId());
        return;
    }

    /**
     * 清空购物车（删除所有商品记录）。
     */
    @Override
    public void clear() {
        // 删除当前登录用户的全部购物车记录
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}