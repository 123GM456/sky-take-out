package com.GM.service;

import com.GM.dto.DishDTO;
import com.GM.dto.DishPageQueryDTO;
import com.GM.entity.Category;
import com.GM.entity.Dish;
import com.GM.result.PageResult;
import com.GM.vo.DishVO;

import java.util.List;

/**
 * 菜品业务逻辑接口（Service 层）。
 */
public interface DishService {

    /**
     * 菜品分页查询。
     * @param dishPageQueryDTO 分页参数（page、pageSize、name、categoryId、status）
     * @return 分页结果（total + 当前页记录列表）
     */
    PageResult<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 新增菜品。
     * @param dishDTO 菜品表单数据（含口味列表）
     */
    void save(DishDTO dishDTO);

    /**
     * 更新菜品。
     * @param dishDTO 更新后的菜品数据（通过 id 字段定位）
     */
    void update(DishDTO dishDTO);

    /**
     * 批量删除菜品。
     * @param ids 待删除的菜品 ID 列表
     */
    void deleteByIds(List<Long> ids);

    /**
     * 按 ID 查询菜品。
     * @param id 菜品 ID
     * @return 菜品实体
     */
    DishVO getById(Long id);

    List<Dish> list(Dish dish);

    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 起售/停售菜品。
     * @param status 目标状态：1=起售，0=停售
     * @param id     菜品 ID
     */
    void setStatus(Integer status, Long id);
}