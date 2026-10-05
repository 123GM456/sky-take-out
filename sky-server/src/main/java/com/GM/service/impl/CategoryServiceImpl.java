package com.GM.service.impl;

import com.GM.constant.StatusConstant;
import com.GM.dto.CategoryDTO;
import com.GM.dto.CategoryPageQueryDTO;
import com.GM.entity.Category;
import com.GM.mapper.CategoryMapper;
import com.GM.result.PageResult;
import com.GM.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类业务逻辑实现（Service 层）。
 */
@Service                         // 标记为 Service 层 Bean，Spring 自动注册
@Slf4j
@RequiredArgsConstructor         // 为 final 字段生成构造器注入
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 分类分页查询。
     * <p>使用 PageHelper 自动拦截下一条 SQL 添加 LIMIT，无需手动拼 SQL。</p>
     */
    @Override
    public PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        List<Category> list = categoryMapper.pageQuery(categoryPageQueryDTO);
        Page<Category> page = (Page<Category>) list;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    /**
     * 新增分类。
     * <p>DTO → Entity 拷贝后，默认启用状态、记录时间戳。</p>
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.ENABLE);                          // 新增默认启用
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insert(category);
    }

    /**
     * 更新分类。
     * <p>只更新 DTO 中非空字段（XML <set> 动态 SQL 实现），自动补充修改时间。</p>
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 按 ID 删除分类。
     */
    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    /**
     * 启用/禁用分类。
     */
    @Override
    public void setStatus(Integer status, Long id) {
        categoryMapper.setStatus(status, id);
    }

    /**
     * 按 ID 查询分类。
     */
    @Override
    public Category getById(Long id) {
        return categoryMapper.getById(id);
    }

    @Override
    public List<Category> list(Category category) {
        return categoryMapper.listByType(category);
    }
}