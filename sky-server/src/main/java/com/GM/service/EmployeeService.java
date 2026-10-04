package com.GM.service;

import com.GM.dto.EmployeeDTO;
import com.GM.dto.EmployeeLoginDTO;
import com.GM.dto.EmployeePageQueryDTO;
import com.GM.entity.Employee;
import com.GM.result.PageResult;
import com.GM.vo.EmployeeLoginVO;

/**
 * 员工业务逻辑接口（Service 层）。
 * <p>定义员工相关业务的对外契约，由 {@code EmployeeServiceImpl} 实现。
 * 使用接口 + 实现类的模式，方便单元测试时 Mock 替换。</p>
 */
public interface EmployeeService {

    /**
     * 员工登录。
     * <p>校验流程：查用户 → 验密码（MD5） → 验状态 → 生成 JWT 令牌。</p>
     *
     * @param employeeLoginDTO 登录请求体（用户名 + 明文密码）
     * @return 登录成功后的视图对象（含员工信息 + JWT 令牌）
     * @throws com.GM.exception.LoginFailedException 账号不存在、密码错误或已被禁用
     */
    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工分页查询（支持按姓名模糊筛选）。
     *
     * @param employeePageQueryDTO 分页参数（page、pageSize、name）
     * @return 分页结果（total + 当前页记录列表）
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 新增员工。
     * <p>自动填充默认密码（MD5）、默认启用状态、时间戳和操作人。</p>
     *
     * @param employeeDTO 前端提交的员工信息（不含 password、status 等系统字段）
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 启用/禁用员工。
     *
     * @param status 目标状态：1=启用，0=禁用
     * @param id     目标员工 ID
     */
    void setStatus(Integer status, Long id);

    /**
     * 按 ID 查询员工详情。
     *
     * @param id 员工 ID
     * @return 员工实体（password 字段已被置空，不返回给前端）
     */
    Employee getById(Long id);

    /**
     * 更新员工信息。
     *
     * @param employeeDTO 更新后的员工数据（通过 id 字段定位被修改的员工）
     */
    void update(EmployeeDTO employeeDTO);
}