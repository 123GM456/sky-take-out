package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工数据访问接口。
 * 使用 MyBatis 注解或 XML 映射文件执行 SQL。
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工。
     * 用户名具有 UNIQUE 约束，所以最多返回一条记录。
     *
     * @param username 用户名
     * @return 员工实体，未找到时返回 null
     */
    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);

    /**
     * 分页查询员工列表（按创建时间倒序）。
     * name 为空时查询全部；不为空时按姓名模糊查询。
     *
     * @param name     员工姓名，可为空
     * @param offset   起始行（从 0 开始）
     * @param pageSize 每页条数
     * @return 员工列表
     */
    @Select("SELECT * FROM employee WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL) ORDER BY create_time DESC LIMIT #{offset}, #{pageSize}")
    List<Employee> pageQuery(String name, Integer offset, Integer pageSize);

    /**
     * 统计员工总数（与分页查询同条件）。
     */
    @Select("SELECT COUNT(*) FROM employee WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL)")
    Long countQuery(String name);

}