package com.GM.controller.admin;

import com.GM.dto.EmployeeDTO;
import com.GM.dto.EmployeeLoginDTO;
import com.GM.dto.EmployeePageQueryDTO;
import com.GM.entity.Employee;
import com.GM.result.PageResult;
import com.GM.result.Result;
import com.GM.service.EmployeeService;
import com.GM.vo.EmployeeLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端 — 员工管理控制器（Controller 层）。
 * <p>接收前端员工管理相关请求（登录、分页查询、新增、编辑、启停等），
 * 调用 Service 层处理并返回统一格式响应。</p>
 */
@RestController                     // 将返回值自动序列化为 JSON 写入 HTTP 响应体
@RequestMapping("/admin/employee")  // 所有方法共用该 URL 前缀
@RequiredArgsConstructor            // 为 final 字段生成构造器注入
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 员工登录。
     * <p>前端 POST → Nginx 转发 → /admin/employee/login，请求体含 username + password。</p>
     *
     * @param employeeLoginDTO 登录请求体（用户名 + 明文密码）
     * @return code=1 时 data 为 EmployeeLoginVO（含员工信息 + JWT token）
     */
    // @RequestBody：将请求体 JSON 反序列化为 EmployeeLoginDTO 对象
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录请求：username={}", employeeLoginDTO.getUsername());
        try {
            EmployeeLoginVO vo = employeeService.login(employeeLoginDTO);
            return Result.success(vo);
        } catch (Exception e) {
            log.warn("登录失败：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 员工分页查询。
     *
     * @param employeePageQueryDTO 分页参数（page、pageSize、name）
     * @return 分页结果（total + records 列表）
     */
    @GetMapping("/page")
    public Result<PageResult<Employee>> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工：{}", employeePageQueryDTO);
        PageResult<Employee> pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 员工退出。
     * <p>前端退出时调用，后端无需特殊处理（前端负责清除本地 token）。</p>
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工。
     * <p>前端 POST → Nginx 转发 → /admin/employee，请求体含员工表单数据。</p>
     *
     * @param employeeDTO 员工表单数据（用户名、姓名、手机号、性别、身份证号）
     */
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 启用/禁用员工。
     * <p>前端 PUT → /admin/employee/status/{status}，通过路径变量传入状态值。</p>
     *
     * @param status 目标状态：1=启用，0=禁用
     * @param id     员工 ID（请求参数，非路径变量）
     */
    // @PathVariable：从 URL 路径中提取 {status} 参数
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("员工启停请求：status={}, id={}", status, id);
        employeeService.setStatus(status, id);
        return Result.success();
    }

    /**
     * 查询员工详情。
     *
     * @param id 员工 ID（路径变量）
     * @return 员工实体（password 已被置空）
     */
    @GetMapping("/{id}")
    public Result<Employee> get(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 更新员工信息。
     *
     * @param employeeDTO 更新后的员工数据（通过 id 字段定位被修改的员工）
     */
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("更新员工：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}