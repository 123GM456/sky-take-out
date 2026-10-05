package com.GM.controller.user;

import com.GM.constant.StatusConstant;
import com.GM.entity.Dish;
import com.GM.result.Result;
import com.GM.service.DishService;
import com.GM.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@RequiredArgsConstructor
@Slf4j
public class DishController {

    private final DishService dishService;

    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {

        //查询redis中有无菜品

        //如果redis中没有菜品，从数据库中查询

        //将查询到的菜品缓存到redis中
        log.info("用户端查询菜品列表：categoryId={}", categoryId);
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }
}