package com.GM.mapper;

import com.GM.entity.Dish;
import com.GM.dto.DishPageQueryDTO;
import com.GM.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 菜品数据访问接口（Mapper / DAO 层）。
 * <p>复杂 SQL（含动态条件）写在对应的 XML 中。</p>
 */
@Mapper  // MyBatis 标记为 Mapper 接口，Spring 自动扫描注册
public interface DishMapper {

    /**
     * 菜品分页查询（XML 中配置，支持按名称和分类筛选）。
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 新增菜品。
     */
    void insert(Dish dish);

    /**
     * 更新菜品（XML 中使用 <set> 动态 SQL，只更新非空字段）。
     */
    void update(Dish dish);

    /**
     * 批量删除菜品（XML 中使用 <foreach> 拼接 IN 条件）。
     */
    void deleteByIds(List<Long> ids);

    /**
     * 按 ID 查询菜品。
     */
    @Select("SELECT * FROM dish WHERE id = #{id}")
    DishVO getById(Long id);

    List<Dish> listByCategoryId(Dish dish);

    /**
     * 起售/停售菜品。
     */
    void setStatus(Integer status, Long id);
}