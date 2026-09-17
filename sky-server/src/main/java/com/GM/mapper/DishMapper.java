package com.GM.mapper;

import com.GM.entity.Dish;
import com.GM.dto.DishPageQueryDTO;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface DishMapper {

    List<Dish> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void insert(Dish dish);

    void update(Dish dish);

    void deleteByIds(List<Long> ids);

    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish getById(Long id);
}
