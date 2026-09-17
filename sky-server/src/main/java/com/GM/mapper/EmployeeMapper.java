package com.GM.mapper;

import com.GM.dto.EmployeePageQueryDTO;
import com.GM.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 员工数据访问接口（Mapper / DAO 层）。
 * <p>定义 employee 表的 CRUD 方法，SQL 写在 {@code resources/mapper/EmployeeMapper.xml} 中。
 * 简单查询用 {@code @Select} 注解直接写 SQL，复杂查询（动态 SQL）用 XML 配置。</p>
 *
 * <p>注意：pageQuery 的 SQL 不允许写 LIMIT，分页由 Service 层 PageHelper 自动拦截完成。</p>
 */
@Mapper  // MyBatis 标记此接口为 Mapper，Spring 启动时自动扫描并注册代理实现
public interface EmployeeMapper {

    /**
     * 按用户名查询员工（登录时检查账号是否存在）。
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 员工分页查询（XML 动态 SQL 按姓名模糊筛选）。
     * <p>Service 层调用前需先执行 PageHelper.startPage()，
     * PageHelper 自动拦截此 SQL 追加 LIMIT 并执行 COUNT。</p>
     */
    List<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 新增员工。
     */
    void insert(Employee employee);

    /**
     * 更新员工。
     * <p>XML 中使用 {@code <set>} 动态 SQL，只更新非空字段。</p>
     */
    void update(Employee employee);

    /**
     * 按 ID 查询员工（用于详情展示或状态校验）。
     */
    @Select("select * from employee where id = #{id}")
    Employee selectById(Long id);
}