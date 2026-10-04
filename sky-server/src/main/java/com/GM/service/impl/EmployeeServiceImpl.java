package com.GM.service.impl;

import com.GM.constant.JwtClaimsConstant;
import com.GM.constant.MessageConstant;
import com.GM.constant.PasswordConstant;
import com.GM.constant.StatusConstant;
import com.GM.context.BaseContext;
import com.GM.dto.EmployeeDTO;
import com.GM.dto.EmployeeLoginDTO;
import com.GM.dto.EmployeePageQueryDTO;
import com.GM.entity.Employee;
import com.GM.exception.LoginFailedException;
import com.GM.mapper.EmployeeMapper;
import com.GM.properties.JwtProperties;
import com.GM.result.PageResult;
import com.GM.service.EmployeeService;
import com.GM.utils.JwtUtil;
import com.GM.vo.EmployeeLoginVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工业务层实现。
 * <p>包含登录校验的核心逻辑：查用户 → 验密码（MD5） → 验状态 → 发令牌。
 * 密码使用 MD5 存储（苍穹外卖项目约定，便于教学演示，生产环境下推荐 BCrypt）。</p>
 */
@Service                         // 标记为 Service 层 Bean，Spring 自动注册
@RequiredArgsConstructor         // 为 final 字段生成构造器注入（替代 @Autowired）
@Slf4j                           // 生成 log 对象（log.info / log.warn）
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    private final JwtProperties jwtProperties;

    /**
     * 员工登录。
     * <p>校验流程：查用户 → 验密码（MD5） → 验状态 → 发令牌。</p>
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
        if (employee.getStatus() == com.GM.constant.StatusConstant.DISABLE) {
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
     * 员工分页查询（按条件查当前页数据 + 统计总数）。
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        List<Employee> list = employeeMapper.pageQuery(employeePageQueryDTO);
        Page<Employee> page = (Page<Employee>) list;
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 新增员工。
     * <p>DTO → Entity 拷贝后，补充默认密码（123456 的 MD5）、启用状态、时间戳和操作人。</p>
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        // DTO → Entity：将同名属性（username、name、phone、sex、idNumber）批量拷贝
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // 使用默认密码（123456）MD5 加密后存入（新增员工时无需用户输入密码）
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        // 新员工默认启用
        employee.setStatus(StatusConstant.ENABLE);
        // 记录创建和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 记录当前登录的管理员 ID 作为创建人和修改人
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

    /**
     * 启用/禁用员工。
     * <p>利用 {@code Employee.builder()} 只设置 id 和 status，Mapper 的 {@code <set>} 动态 SQL
     * 只会更新 status 字段，不会影响其他字段。</p>
     */
    @Override
    public void setStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 按 ID 查询员工详情。
     * <p>返回前将 password 置空，避免将敏感信息暴露给前端。</p>
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        employee.setPassword(null);
        return employee;
    }

    /**
     * 更新员工信息。
     * <p>只更新 DTO 中非空字段（XML {@code <set>} 动态 SQL 实现），
     * 自动补充修改时间和操作人。</p>
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}