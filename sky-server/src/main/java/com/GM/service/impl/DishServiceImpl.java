package com.GM.service.impl;

import com.GM.constant.StatusConstant;
import com.GM.context.BaseContext;
import com.GM.dto.DishDTO;
import com.GM.dto.DishPageQueryDTO;
import com.GM.entity.Dish;
import com.GM.entity.DishFlavor;
import com.GM.mapper.DishFlavorMapper;
import com.GM.mapper.DishMapper;
import com.GM.result.PageResult;
import com.GM.service.DishService;
import com.GM.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品业务逻辑实现（Service 层）。
 */
@Service                         // 标记为 Service 层 Bean，Spring 自动注册
@Slf4j
@RequiredArgsConstructor         // 为 final 字段生成构造器注入
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    private final DishFlavorMapper dishFlavorMapper;

    /**
     * 菜品分页查询。
     * <p>使用 PageHelper 自动拦截下一条 SQL 添加 LIMIT。</p>
     */
    @Override
    public PageResult<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增菜品。
     * <p>DTO → Entity 拷贝后，记录创建/修改时间戳和操作人。</p>
     */
    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        // 记录当前登录的管理员 ID 作为创建人和修改人
        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateUser(BaseContext.getCurrentId());
        dishMapper.insert(dish);
    }

    /**
     * 更新菜品。
     * <p>只更新 DTO 中非空字段（XML <set> 动态 SQL 实现），自动补充修改时间和修改人。</p>
     */
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setUpdateTime(LocalDateTime.now());
        // 记录当前登录的管理员 ID 作为修改人（创建人不变）
        dish.setUpdateUser(BaseContext.getCurrentId());
        dishMapper.update(dish);
    }

    /**
     * 批量删除菜品。
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        dishMapper.deleteByIds(ids);
    }

    /**
     * 按 ID 查询菜品。
     */
    @Override
    public DishVO getById(Long id) {
        return dishMapper.getById(id);
    }

    @Override
    public List<Dish> list(Dish dish) {
        return dishMapper.listByCategoryId(dish);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.listByCategoryId(dish);

        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : dishList) {
            DishVO vo = new DishVO();
            BeanUtils.copyProperties(d, vo);
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());
            vo.setFlavors(flavors);
            dishVOList.add(vo);
        }
        return dishVOList;
    }

    /**
     * 起售/停售菜品。
     */
    @Override
    public void setStatus(Integer status, Long id) {
        dishMapper.setStatus(status, id);
    }
}