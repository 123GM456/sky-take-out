package com.GM.service;

import com.GM.dto.DishDTO;
import com.GM.dto.DishPageQueryDTO;
import com.GM.entity.Dish;
import com.GM.result.PageResult;
import java.util.List;

public interface DishService {

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void save(DishDTO dishDTO);

    void update(DishDTO dishDTO);

    void deleteByIds(List<Long> ids);

    Dish getById(Long id);
}
