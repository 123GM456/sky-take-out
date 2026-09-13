package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 员工登录 DTO（数据传输对象），接收前端登录表单提交的数据。
 */
@Data
public class EmployeeLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录账号 */
    private String username;

    /** 明文密码（由 Service 层做 MD5 加密后再比对） */
    private String password;

}