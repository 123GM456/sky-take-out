package com.GM.controller.admin;

import com.GM.dto.DishDTO;
import com.GM.dto.DishPageQueryDTO;
import com.GM.entity.Category;
import com.GM.entity.Dish;
import com.GM.result.PageResult;
import com.GM.result.Result;
import com.GM.service.DishService;
import com.GM.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理端 — 菜品管理控制器（Controller 层）。
 * <p>接收前端菜品管理请求（新增、分页、批量删除、修改、起停售、详情），调用 Service 处理。</p>
 */
@RestController("adminDishController")  // 指定 Bean 名称，避免与用户端同名 Controller 冲突
@RequestMapping("/admin/dish")      // 所有方法公用 URL 前缀
@RequiredArgsConstructor            // 为 final 字段生成构造器注入
@Slf4j
public class DishController {

    private final DishService dishService;

    /**
     * 新增菜品（含口味选项）。
     */
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询。
     *
     * @param dishPageQueryDTO 分页参数（page、pageSize、name、categoryId、status）
     */
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品。
     *
     * @param ids 待删除的菜品 ID 列表（查询参数，如 ?ids=1&ids=2）
     */
    // @RequestParam：将查询参数 ids 绑定为 List<Long>
    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Long> ids) {
        log.info("批量删除菜品：{}", ids);
        dishService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 修改菜品。
     */
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    /**
     * 起售/停售菜品。
     *
     * @param status 目标状态：1=起售，0=停售
     * @param id     菜品 ID
     */
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("起售停售菜品：status={}, id={}", status, id);
        dishService.setStatus(status, id);
        return Result.success();
    }

    /**
     * 查询菜品详情。
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 根据类型查询菜品列表（下拉框用）。
     */
    @GetMapping
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

}