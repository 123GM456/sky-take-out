package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 员工 DTO（数据传输对象），接收前端员工表单数据。
 * <p>新增时由 Service 自动填充默认密码；编辑时 id 字段标识被修改的员工。</p>
 *
 * <p>说明：不包含 password、status、createTime 等系统字段，这些由后端自动处理。</p>
 */
@Data
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 员工 ID（新增时为 null，编辑时必填） */
    private Long id;

    /** 登录账号（唯一，不能重复） */
    private String username;

    /** 员工真实姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别：1=男，0=女 */
    private String sex;

    /** 身份证号 */
    private String idNumber;
}