package com.GM.mapper;

import com.GM.entity.Category;
import com.GM.dto.CategoryPageQueryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 分类数据访问接口（Mapper / DAO 层）。
 * <p>简单 SQL 用注解实现，复杂 SQL（含动态条件）写在对应的 XML 中。</p>
 */
@Mapper  // MyBatis 标记为 Mapper 接口，Spring 自动扫描注册
public interface CategoryMapper {

    /**
     * 分类分页查询（XML 中配置，支持按名称模糊匹配和按类型筛选）。
     */
    List<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类。
     */
    @Insert("INSERT INTO category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "VALUES (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    /**
     * 更新分类（XML 中使用 <set> 动态 SQL，只更新非空字段）。
     */
    void update(Category category);

    /**
     * 按 ID 删除分类。
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * 启用/禁用分类。
     */
    // @Param：给参数命名，注解 SQL 中可通过 #{status}、#{id} 引用
    @Update("UPDATE category SET status = #{status} WHERE id = #{id}")
    void setStatus(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 按 ID 查询分类。
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getById(Long id);

    List<Category> listByType(@Param("type") Long type);
}