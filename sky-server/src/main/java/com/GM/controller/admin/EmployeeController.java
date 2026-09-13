package com.GM.controller.admin;

// 项目 DTO：接收登录请求的请求体（username + password）
import com.GM.dto.EmployeeLoginDTO;
// 项目 DTO：接收分页查询参数（page、pageSize、name）
import com.GM.dto.EmployeePageQueryDTO;
// 项目工具：统一响应封装（所有的 Controller 方法统一返回 Result<T>）
import com.GM.result.Result;
// 项目工具：分页结果封装（含 total + records）
import com.GM.result.PageResult;
// 项目 Service：员工业务逻辑接口，调用其 login() / pageQuery() 方法
import com.GM.service.EmployeeService;
// 项目 VO：登录成功后返回给前端的视图对象（含员工信息 + JWT token）
import com.GM.vo.EmployeeLoginVO;
// Lombok：为 final 字段生成构造器注入（employeeService 不需要手写 @Autowired）
import lombok.RequiredArgsConstructor;
// Lombok：为当前类生成 log 日志对象
import lombok.extern.slf4j.Slf4j;
// Spring MVC：映射 HTTP GET 请求到处理方法
import org.springframework.web.bind.annotation.GetMapping;
// Spring MVC：映射 HTTP POST 请求到处理方法
import org.springframework.web.bind.annotation.PostMapping;
// Spring MVC：将 HTTP 请求体中的 JSON 绑定到方法参数（@RequestBody EmployeeLoginDTO）
import org.springframework.web.bind.annotation.RequestBody;
// Spring MVC：在类级别定义路径前缀 @RequestMapping("/admin/employee")
import org.springframework.web.bind.annotation.RequestMapping;
// Spring MVC：标记当前类为 REST 风格控制器（@Controller + @ResponseBody 的组合）
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端 — 员工管理控制器（Controller 层）。
 * <p>接收前端登录请求，调用 Service 完成校验，返回统一格式响应。</p>
 */
// Spring MVC：标记为 Controller，方法返回值直接写入 HTTP 响应体（相当于 @Controller + @ResponseBody）
@RestController
// 映射该控制器所有方法的请求路径前缀，统一 "/admin/employee"
@RequestMapping("/admin/employee")
// Lombok：为 final 字段生成构造器注入（避免手写 @Autowired）
@RequiredArgsConstructor
// Lombok：生成 log 日志对象
@Slf4j
// Controller：接收前端员工管理相关请求，调用 Service 层处理并返回统一格式响应
public class EmployeeController {

    // 通过 @RequiredArgsConstructor 自动注入，等效于 @Autowired
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
    // Spring MVC：映射 HTTP POST 请求到 /admin/employee/login
    @PostMapping("/login")
    // @RequestBody：将请求体中的 JSON 自动反序列化为 EmployeeLoginDTO 对象
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
    // Spring MVC：映射 HTTP GET 请求到 /admin/employee/page
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        return Result.success(employeeService.pageQuery(employeePageQueryDTO));
    }

    /**
     * 员工退出。
     * 前端退出登录时调用，后端无需特殊处理（前端负责清除本地 token）。
     */
    // Spring MVC：映射 HTTP POST 请求到 /admin/employee/logout
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 员工新增。
     * <p>前端 POST 请求路径 /api/employee/save，Nginx 转发为 /admin/employee/save。
     * 请求体包含员工信息。</p>
     *
     * @param employeeDTO 员工新增请求体（用户名 + 姓名 + 手机号 + 性别 + 身份证号）
     * @return code=1 时 data 为 null，code=0 时 msg 为错误原因
     */
    // Spring MVC：映射 HTTP POST 请求到 /admin/employee/save
    @PostMapping("/save")
    // @RequestBody：将请求体中的 JSON 自动反序列化为 EmployeeDTO 对象
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("员工新增请求：username={}", employeeDTO.getUsername());
        return Result.success();
    }

}