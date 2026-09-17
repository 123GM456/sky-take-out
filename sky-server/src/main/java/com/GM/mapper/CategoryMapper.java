package com.GM.mapper;

import com.GM.entity.Category;
import com.GM.dto.CategoryPageQueryDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CategoryMapper {

    List<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Insert("INSERT INTO category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "VALUES (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    void update(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void deleteById(Long id);

    @Update("UPDATE category SET status = #{status} WHERE id = #{id}")
    void startOrStop(@Param("status") Integer status, @Param("id") Long id);

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getById(Long id);
}
