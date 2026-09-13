package com.GM.service;

// 项目 DTO：接收前端登录请求体（用户名 + 密码）
import com.GM.dto.EmployeeLoginDTO;
// 项目 DTO：接收前端分页查询参数（page、pageSize、name）
import com.GM.dto.EmployeePageQueryDTO;
// 项目 VO：封装分页返回结果（total + records 列表）
import com.GM.result.PageResult;
// 项目 VO：封装登录成功返回给前端的员工信息 + JWT 令牌
import com.GM.vo.EmployeeLoginVO;

/**
 * 员工业务逻辑接口。
 */
// Service 接口：定义员工业务方法（登录、分页查询等），由 EmployeeServiceImpl 实现
public interface EmployeeService {

    /**
     * 员工登录。
     *
     * @param employeeLoginDTO 登录信息（用户名、密码）
     * @return 登录结果（含 id、name、username、token）
     * @throws com.GM.exception.LoginFailedException 登录失败时抛出
     */
    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工分页查询。
     *
     * @param employeePageQueryDTO 分页参数（page、pageSize、name）
     * @return 分页结果（total + records）
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

}