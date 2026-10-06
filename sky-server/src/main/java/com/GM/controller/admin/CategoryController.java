package com.GM.controller.admin;

import com.GM.dto.CategoryDTO;
import com.GM.dto.CategoryPageQueryDTO;
import com.GM.entity.Category;
import com.GM.result.PageResult;
import com.GM.result.Result;
import com.GM.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端 — 分类管理控制器（Controller 层）。
 * <p>接收前端分类管理请求（新增、分页、删除、修改、启停、详情），调用 Service 处理。</p>
 */
@RestController("adminCategoryController")  // 指定 Bean 名称，避免与用户端同名 Controller 冲突
@RequestMapping("/admin/category")  // 所有方法公用 URL 前缀
@RequiredArgsConstructor            // 为 final 字段生成构造器注入
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 新增分类。
     */
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询。
     *
     * @param categoryPageQueryDTO 分页参数（page、pageSize、name、type）
     */
    @GetMapping("/page")
    public Result<PageResult<Category>> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询：{}", categoryPageQueryDTO);
        PageResult<Category> pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除分类。
     *
     * @param id 分类 ID（请求参数）
     */
    @DeleteMapping
    public Result deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改分类。
     */
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 启用/禁用分类。
     *
     * @param status 目标状态（路径变量，1=启用，0=禁用）
     * @param id     分类 ID（请求参数）
     */
    // @PathVariable：从 URL 路径中提取 {status} 参数
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("启用禁用分类：status={}, id={}", status, id);
        categoryService.setStatus(status, id);
        return Result.success();
    }

    /**
     * 查询分类详情。
     */
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @GetMapping("/list")
    public Result<List<Category>> list(Long type) {
        log.info("管理端查询分类列表：type={}", type);
        Category category = new Category();
        category.setType(type.intValue());

        List<Category> list = categoryService.list(category);
        return Result.success(list);
    }
}