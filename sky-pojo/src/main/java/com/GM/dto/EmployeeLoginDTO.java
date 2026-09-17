package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 员工登录 DTO，接收前端登录表单提交的用户名和密码。
 * <p>Service 层收到后进行 MD5 加密比对，而非直接比对明文。</p>
 */
@Data
public class EmployeeLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录账号 */
    private String username;

    /** 明文密码（Service 层会做 MD5 加密后再与数据库密文比对） */
    private String password;
}