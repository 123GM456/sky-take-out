package com.GM.service;

import com.GM.dto.CategoryDTO;
import com.GM.dto.CategoryPageQueryDTO;
import com.GM.entity.Category;
import com.GM.result.PageResult;
import java.util.List;

/**
 * 分类业务逻辑接口（Service 层）。
 */
public interface CategoryService {

    /**
     * 分类分页查询。
     *
     * @param categoryPageQueryDTO 分页参数（page、pageSize、name、type）
     * @return 分页结果（total + 当前页记录列表）
     */
    PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类。
     *
     * @param categoryDTO 分类表单数据
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 更新分类。
     *
     * @param categoryDTO 更新后的分类数据（通过 id 字段定位）
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 按 ID 删除分类。
     *
     * @param id 分类 ID
     */
    void deleteById(Long id);

    /**
     * 启用/禁用分类。
     *
     * @param status 目标状态：1=启用，0=禁用
     * @param id     目标分类 ID
     */
    void setStatus(Integer status, Long id);

    /**
     * 按 ID 查询分类。
     *
     * @param id 分类 ID
     * @return 分类实体
     */
    Category getById(Long id);

    List<Category> list(Category category);
}