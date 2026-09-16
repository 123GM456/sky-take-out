package com.GM.service;

// 项目 DTO：接收前端新增员工表单数据（用户名、姓名、手机号、性别、身份证号）
import com.GM.dto.EmployeeDTO;
// 项目 DTO：接收前端登录请求体（用户名 + 密码）
import com.GM.dto.EmployeeLoginDTO;
// 项目 DTO：接收前端分页查询参数（page、pageSize、name）
import com.GM.dto.EmployeePageQueryDTO;
// 项目结果：封装分页返回结果（total + records 列表）
import com.GM.entity.Employee;
import com.GM.result.PageResult;
// 项目 VO：封装登录成功返回给前端的员工信息 + JWT 令牌
import com.GM.vo.EmployeeLoginVO;

/**
 * 员工业务逻辑接口。
 */
public interface EmployeeService {

    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void save(EmployeeDTO employeeDTO);

    void startOrStop(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);

    void removeById(Long id);
}