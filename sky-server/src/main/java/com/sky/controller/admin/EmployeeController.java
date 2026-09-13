package com.sky.controller.admin;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.result.Result;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端 — 员工管理控制器（Controller 层）。
 * <p>接收前端登录请求，调用 Service 完成校验，返回统一格式响应。</p>
 */
@RestController
@RequestMapping("/admin/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 员工登录。
     * <p>前端 POST 请求路径 /api/employee/login，Nginx 转发为 /admin/employee/login。
     * 请求体包含 username 和 password。</p>
     *
     * @param employeeLoginDTO 登录请求体（用户名 + 明文密码）
     * @return code=1 时 data 为 EmployeeLoginVO（含 id、name、username、token）；
     *         code=0 时 msg 为错误原因
     */
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
     * @return 分页结果（total + records）
     */
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        return Result.success(employeeService.pageQuery(employeePageQueryDTO));
    }

}