package com.GM.mapper;

import com.GM.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<SetmealDish> getBySetmealId(Long setmealId);
}