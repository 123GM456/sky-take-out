package com.GM.service.impl;

import com.GM.dto.DishDTO;
import com.GM.dto.DishPageQueryDTO;
import com.GM.entity.Dish;
import com.GM.mapper.DishMapper;
import com.GM.result.PageResult;
import com.GM.service.DishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        List<Dish> list = dishMapper.pageQuery(dishPageQueryDTO);
        Page<Dish> page = (Page<Dish>) list;
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        dishMapper.insert(dish);
    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setUpdateTime(LocalDateTime.now());
        dishMapper.update(dish);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        dishMapper.deleteByIds(ids);
    }

    @Override
    public Dish getById(Long id) {
        return dishMapper.getById(id);
    }
}
