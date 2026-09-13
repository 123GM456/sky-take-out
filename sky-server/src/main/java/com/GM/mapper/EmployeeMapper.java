package com.GM.mapper;

// 项目实体：员工 ORM 映射对象，与 employee 表字段一一对应
import com.GM.entity.Employee;
// MyBatis：将接口标记为 Mapper（Spring 会自动生成代理实现类）
import org.apache.ibatis.annotations.Mapper;
// MyBatis：注解式 SQL 查询，将方法直接映射为 SELECT 语句
import org.apache.ibatis.annotations.Select;
// JDK：方法返回员工列表时使用
import java.util.List;

/**
 * 员工数据访问接口。
 * 使用 MyBatis 注解或 XML 映射文件执行 SQL。
 */
// MyBatis：标记为 Mapper 接口，Spring 会为其生成代理实现类
@Mapper
// Mapper：定义员工表的数据访问方法，使用 MyBatis 注解映射 SQL 语句
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工。
     * 用户名具有 UNIQUE 约束，所以最多返回一条记录。
     *
     * @param username 用户名
     * @return 员工实体，未找到时返回 null
     */
    // MyBatis：将方法映射为 SQL 查询，通过 #{} 占位符传参，防 SQL 注入
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
    // MyBatis：直接写在注解中的 SQL，含模糊查询 + 分页（LIMIT 是 MySQL 方言）
    @Select("SELECT * FROM employee WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL) ORDER BY create_time DESC LIMIT #{offset}, #{pageSize}")
    List<Employee> pageQuery(String name, Integer offset, Integer pageSize);

    /**
     * 统计员工总数（与分页查询同条件）。
     */
    // MyBatis：使用相同的过滤条件做 COUNT，确保 total 与 records 一致
    @Select("SELECT COUNT(*) FROM employee WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL)")
    Long countQuery(String name);

}