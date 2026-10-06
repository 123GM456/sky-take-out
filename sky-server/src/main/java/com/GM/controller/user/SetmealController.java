package com.GM.controller.user;

import com.GM.constant.StatusConstant;
import com.GM.entity.Setmeal;
import com.GM.result.Result;
import com.GM.service.SetmealService;
import com.GM.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@RequiredArgsConstructor
@Slf4j
public class SetmealController {

    private final SetmealService setmealService;

    /**
     * 根据分类ID查询起售套餐列表。
     *
     * @param categoryId 套餐分类ID
     */
    @GetMapping("/list")
    // 缓存键：根据套餐分类ID动态生成，避免缓存穿透
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("用户端查询套餐列表：categoryId={}", categoryId);
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    /**
     * 根据ID查询套餐详情（含包含的菜品列表）。
     *
     * @param id 套餐ID
     */
    @GetMapping("/dish/{id}")
    public Result<SetmealVO> dish(@PathVariable Long id) {
        log.info("用户端查询套餐详情：id={}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }
}