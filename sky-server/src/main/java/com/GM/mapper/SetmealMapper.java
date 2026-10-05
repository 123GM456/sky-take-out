package com.GM.mapper;

import com.GM.dto.SetmealPageQueryDTO;
import com.GM.entity.Setmeal;
import com.GM.vo.SetmealVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 套餐数据访问接口（Mapper / DAO 层）。
 * <p>复杂 SQL（含动态条件、JOIN）写在对应的 XML 中。</p>
 */
@Mapper
public interface SetmealMapper {

    /**
     * 新增套餐。
     */
    void insert(Setmeal setmeal);

    /**
     * 更新套餐（XML 中使用 <set> 动态 SQL，只更新非空字段）。
     */
    void update(Setmeal setmeal);

    /**
     * 起售/停售套餐。
     */
    @Update("UPDATE setmeal SET status = #{status} WHERE id = #{id}")
    void setStatus(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 批量删除套餐（XML 中使用 <foreach> 拼接 IN 条件）。
     */
    void deleteByIds(List<Long> ids);

    /**
     * 套餐分页查询（XML 中 JOIN category 表获取分类名称）。
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 按 ID 查询套餐详情（XML 中 JOIN category 表获取分类名称）。
     */
    SetmealVO getById(Long id);

    /**
     * 用户端：根据条件查询起售套餐。
     */
    List<Setmeal> listByCategoryId(Setmeal setmeal);

    /**
     * 用户端：根据ID查询套餐详情（含分类名称，不含菜品列表）。
     */
    SetmealVO getUserById(Long id);
}