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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

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

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 新增菜品（含口味选项）。
     */
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.save(dishDTO);
        // 新增菜品只影响其所属分类，精确清除该分类的缓存
        redisTemplate.delete("dish_" + dishDTO.getCategoryId());
        return Result.success();
    }

    /**
     * 菜品分页查询。
     *
     * @param dishPageQueryDTO 分页参数（page、pageSize、name、categoryId、status）
     */
    @GetMapping("/page")
    public Result<PageResult<DishVO>> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult<DishVO> pageResult = dishService.pageQuery(dishPageQueryDTO);
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
        // 批量删除涉及多个分类，整体清除菜品缓存
        clearDishCache();
        return Result.success();
    }

    /**
     * 修改菜品。
     */
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.update(dishDTO);
        // 修改菜品可能更换分类，旧分类缓存无法定位，整体清除菜品缓存
        clearDishCache();
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
        // 起售/停售只拿到菜品ID，定位其分类需额外查库，整体清除菜品缓存
        clearDishCache();
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
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("管理端查询菜品列表：categoryId={}", categoryId);
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);

        List<Dish> list = dishService.list(dish);
        return Result.success(list);
    }

    /**
     * 清除用户端全部菜品缓存（key 统一以 dish_ 为前缀）。
     * <p>修改、批量删除、起售/停售会影响多条菜品缓存，且部分场景无法定位分类ID，故整体清除。</p>
     */
    private void clearDishCache() {
        // 匹配所有 dish_ 前缀的缓存 key（用户端 dish_{categoryId}）
        Set<String> keys = redisTemplate.keys("dish_*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

}