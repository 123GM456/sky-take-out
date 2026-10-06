package com.GM.controller.user;

import com.GM.dto.ShoppingCartDTO;
import com.GM.entity.ShoppingCart;
import com.GM.result.Result;
import com.GM.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户端 — 购物车控制器（Controller 层）。
 * <p>接收微信小程序购物车相关请求（添加、查询、减一、清空），调用 Service 处理。</p>
 */
@RestController("userShoppingCartController")  // 指定 Bean 名称，避免与其他端同名 Controller 冲突
@RequestMapping("/user/shoppingCart")          // 所有方法公用 URL 前缀
@RequiredArgsConstructor                       // 为 final 字段生成构造器注入
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * 查看购物车（当前用户的购物车列表）。
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    /**
     * 添加购物车（dishId 与 setmealId 二选一）。
     */
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }
    
    /**
     * 减少购物车商品数量。
     * <p>若商品数量为 1，则删除该商品记录。</p>
     */
    @PostMapping("/sub")
    public  Result  reduce(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("减少商品：{}", shoppingCartDTO);
        shoppingCartService.reduce(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 清空购物车（删除所有商品记录）。
     */
    @DeleteMapping("/clean")
    public Result clear() {
        log.info("清空购物车");
        shoppingCartService.clear();
        return Result.success();
    }

}