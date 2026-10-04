package com.GM.service;

import com.GM.dto.SetmealPageQueryDTO;
import com.GM.entity.Setmeal;
import com.GM.result.PageResult;
import com.GM.vo.SetmealVO;

import java.util.List;

/**
 * 套餐业务逻辑接口（Service 层）。
 */
public interface SetmealService {

    /**
     * 新增套餐。
     * @param setmeal 套餐数据
     */
    void save(Setmeal setmeal);

    /**
     * 更新套餐。
     * @param setmeal 更新后的套餐数据
     */
    void update(Setmeal setmeal);

    /**
     * 起售/停售套餐。
     * @param status 目标状态：1=起售，0=停售
     * @param id     套餐 ID
     */
    void setStatus(Integer status, Long id);

    /**
     * 批量删除套餐。
     * @param ids 待删除的套餐 ID 列表
     */
    void deleteByIds(List<Long> ids);

    /**
     * 套餐分页查询。
     * @param setmealPageQueryDTO 分页参数（page、pageSize、name、categoryId、status）
     * @return 分页结果（total + 当前页记录列表）
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 按 ID 查询套餐详情。
     * @param id 套餐 ID
     * @return 套餐详情（含分类名称）
     */
    SetmealVO getById(Long id);
}