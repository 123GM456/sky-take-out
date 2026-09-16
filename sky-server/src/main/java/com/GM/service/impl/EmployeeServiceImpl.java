package com.GM.service.impl;

// 项目常量：JWT 声明键名（EMPLOYEE_ID），用于生成/解析 token 时存取员工 ID

import com.GM.constant.JwtClaimsConstant;
// 项目常量：业务文案信息（如 "账号不存在"、"密码错误"），统一管理避免硬编码
import com.GM.constant.MessageConstant;
import com.GM.constant.PasswordConstant;
import com.GM.constant.StatusConstant;
import com.GM.context.BaseContext;
// 项目 DTO：接收前端登录请求体（用户名 + 明文密码）
import com.GM.dto.EmployeeDTO;
import com.GM.dto.EmployeeLoginDTO;
// 项目 DTO：接收前端分页查询参数（page、pageSize、name）
import com.GM.dto.EmployeePageQueryDTO;
// 项目实体：员工 ORM 映射对象，MyBatis 查询结果映射为此类型
import com.GM.entity.Employee;
// 项目异常：登录失败时抛出自定义异常，由全局异常处理器统一捕获
import com.GM.exception.LoginFailedException;
// 项目 Mapper：数据访问层，查询数据库操作
import com.GM.mapper.EmployeeMapper;
// 项目工具：分页结果封装（total + records）
import com.GM.result.PageResult;
// 项目 Service：当前实现类所实现的接口
import com.GM.service.EmployeeService;
// 项目工具：JWT 令牌的创建和解析
import com.GM.utils.JwtUtil;
// 项目 VO：登录成功后返回的视图对象（含员工信息 + token）
import com.GM.vo.EmployeeLoginVO;
// PageHelper：分页插件，拦截下一条 SQL 自动加 LIMIT 和 COUNT
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
// Lombok：为 final 字段生成构造器注入（employeeMapper、jwtProperties 不需要手动 @Autowired）
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import lombok.RequiredArgsConstructor;
// Lombok：为当前类生成 log 日志对象
import lombok.extern.slf4j.Slf4j;
// Spring：标记当前类为 Service 层组件，纳入 Spring 容器管理
import org.springframework.stereotype.Service;
// Spring：MD5 加密工具，spring-core 模块自带，无需额外依赖
import java.time.LocalDateTime;
import java.util.HashMap;
// JDK：Map 类型，承载 JWT payload 数据
import java.util.Map;
import java.util.List;

/**
 * 员工业务层实现。
 * <p>包含登录校验的核心逻辑：查用户 → 验密码 → 验状态 → 发令牌。
 * 密码使用 MD5 存储（苍穹外卖项目约定，非安全推荐，但便于教学演示）。</p>
 */
// Spring：标记为 Service 层 Bean，自动注册到 Spring 容器
@Service
// Lombok：为 final 字段生成构造器注入
@RequiredArgsConstructor
// Lombok：生成 log 日志对象
@Slf4j
// Service 实现：实现员工登录校验、分页查询等业务逻辑，调用 Mapper 操作数据库
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    private final com.GM.config.JwtProperties jwtProperties;

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
    // @Override：通知编译器当前方法覆写了接口方法，签名不一致时报错
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
     * 员工分页查询：按条件查当前页数据 + 统计总数。
     */
    // @Override：通知编译器当前方法覆写了接口方法
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
     * <p>DTO → Entity 属性拷贝，补充默认密码、状态、时间戳和操作人信息。</p>
     *
     * @param employeeDTO 前端提交的员工信息（不含 password、status 等系统字段）
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        System.out.println("当前线程的id：" + Thread.currentThread().getId());
        // DTO → Entity：将同名属性（username、name、phone、sex、idNumber）批量拷贝到 Employee 对象
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // 使用默认密码（123456）MD5 加密后存入，新增员工时无需用户输入密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        // 新员工默认启用（1），无需管理员手动开启
        employee.setStatus(StatusConstant.ENABLE);
        // 记录创建和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // 创建人和修改人ID
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        // 插入数据库
        employeeMapper.insert(employee);
    }

    /**
     * 启用/禁用员工。
     */
    // @Override：通知编译器当前方法覆写了接口方法
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 按 ID查询员工（登录后校验账号状态）。
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        employee.setPassword(null);
        return employee;
    }

    /**
     * 更新员工信息。
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

    /**
     * 删除员工。
     */
    @Override
    public void removeById(Long id) {
        employeeMapper.deleteById(id);
    }
}