package com.sky.service.impl;

import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工业务层实现。
 * <p>包含登录校验的核心逻辑：查用户 → 验密码 → 验状态 → 发令牌。
 * 密码使用 MD5 存储（苍穹外卖项目约定，非安全推荐，但便于教学演示）。</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    private final com.sky.config.JwtProperties jwtProperties;

    /**
     * 员工登录。
     * <p>
     * 校验流程：
     * 1. 根据用户名查询员工
     * 2. 校验员工是否存在
     * 3. 校验密码（MD5 加密比较）
     * 4. 校验账号状态
     * 5. 生成 JWT 令牌返回
     */
    @Override
    public EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 1. 根据用户名查询员工
        Employee employee = employeeMapper.getByUsername(username);

        // 2. 账号不存在
        if (employee == null) {
            log.warn("登录失败，账号不存在：{}", username);
            throw new LoginFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 3. 校验密码
        //    将前端传入的明文密码进行 MD5 加密，再与数据库中存储的 MD5 密文比较
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(employee.getPassword())) {
            log.warn("登录失败，密码错误：{}", username);
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }

        // 4. 校验账号状态
        if (employee.getStatus() == com.sky.constant.StatusConstant.DISABLE) {
            log.warn("登录失败，账号被禁用：{}", username);
            throw new LoginFailedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 5. 登录成功，生成 JWT 令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMPLOYEE_ID, employee.getId());

        String token = JwtUtil.createToken(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        log.info("员工登录成功：id={}, username={}", employee.getId(), username);
        return EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .username(employee.getUsername())
                .token(token)
                .build();
    }

    /**
     * 员工分页查询：按条件查当前页数据 + 统计总数。
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        int offset = (employeePageQueryDTO.getPage() - 1) * employeePageQueryDTO.getPageSize();
        List<Employee> records = employeeMapper.pageQuery(
                employeePageQueryDTO.getName(), offset, employeePageQueryDTO.getPageSize());
        Long total = employeeMapper.countQuery(employeePageQueryDTO.getName());
        return new PageResult(total, records);
    }

}