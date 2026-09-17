package com.GM.service;

import com.GM.dto.CategoryDTO;
import com.GM.dto.CategoryPageQueryDTO;
import com.GM.entity.Category;
import com.GM.result.PageResult;
import java.util.List;

public interface CategoryService {

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void save(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void deleteById(Long id);

    void startOrStop(Integer status, Long id);

    Category getById(Long id);
}
