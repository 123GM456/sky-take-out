package com.GM.service.impl;

import com.GM.dto.SetmealPageQueryDTO;
import com.GM.entity.Setmeal;
import com.GM.entity.SetmealDish;
import com.GM.mapper.SetmealDishMapper;
import com.GM.mapper.SetmealMapper;
import com.GM.result.PageResult;
import com.GM.service.SetmealService;
import com.GM.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 套餐业务逻辑实现（Service 层）。
 */
@Service
@RequiredArgsConstructor
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;

    private final SetmealDishMapper setmealDishMapper;

    @Override
    public void save(Setmeal setmeal) {
        setmealMapper.insert(setmeal);
    }

    @Override
    public void update(Setmeal setmeal) {
        setmealMapper.update(setmeal);
    }

    /**
     * 起售/停售套餐。
     */
    @Override
    public void setStatus(Integer status, Long id) {
        setmealMapper.setStatus(status, id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        setmealMapper.deleteByIds(ids);
    }

    /**
     * 套餐分页查询。
     * <p>使用 PageHelper 自动拦截下一条 SQL 添加 LIMIT。</p>
     */
    @Override
    public PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    /**
     * 按 ID 查询套餐详情。
     */
    @Override
    public SetmealVO getById(Long id) {
        return setmealMapper.getById(id);
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.listByCategoryId(setmeal);
    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        SetmealVO setmealVO = setmealMapper.getUserById(id);
        if (setmealVO != null) {
            List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
            setmealVO.setSetmealDishes(setmealDishes);
        }
        return setmealVO;
    }
}