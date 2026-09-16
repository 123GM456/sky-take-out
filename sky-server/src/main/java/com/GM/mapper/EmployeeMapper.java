package com.GM.mapper;

// 项目 DTO：分页查询参数（XML SQL 中会取其 name 字段做模糊匹配）
import com.GM.dto.EmployeePageQueryDTO;
// 项目实体：MyBatis ORM 映射对象
import com.GM.entity.Employee;
// MyBatis：声明 Mapper 接口，启动时自动扫描注册
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工数据访问接口。
 * <p>定义 employee 表的 CRUD 方法，SQL 统一写在 resources/mapper/EmployeeMapper.xml 中。
 * XML 支持动态 SQL（if/where/foreach），比注解方式更适合复杂查询。</p>
 * <p>注意：pageQuery 的 SQL 里不能写 LIMIT，分页由 Service 层 PageHelper 自动拦截完成。</p>
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 按用户名查询员工（登录时校验账号是否存在）。
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 员工分页查询（XML 中已配置动态条件，支持按姓名模糊筛选）。
     * <p>Service 层调用前会先执行 PageHelper.startPage()，
     * PageHelper 会自动拦截这条 SQL 加上 LIMIT，并额外执行 COUNT 查询。</p>
     */
    List<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 新增员工。
     */
    void insert(Employee employee);

    void update(Employee employee);

    /**
     * 按 ID查询员工（登录后校验账号状态）。
     */
    @Select("select * from employee where id = #{id}")
    Employee selectById(Long id);

    void deleteById(Long id);
}