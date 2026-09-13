package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.EmployeeLoginVO;

/**
 * 员工业务逻辑接口。
 */
public interface EmployeeService {

    /**
     * 员工登录。
     *
     * @param employeeLoginDTO 登录信息（用户名、密码）
     * @return 登录结果（含 id、name、username、token）
     * @throws com.sky.exception.LoginFailedException 登录失败时抛出
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
