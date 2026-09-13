package com.GM.dto;

// Lombok：自动生成 getter/setter、toString、equals、hashCode
import lombok.Data;
// JDK：实现序列化接口，支持 DTO 对象跨线程/跨网络传输
import java.io.Serializable;

/**
 * 员工登录 DTO（数据传输对象），接收前端登录表单提交的数据。
 */
// Lombok：自动生成 getter/setter、toString、equals、hashCode、canEqual
@Data
// DTO：接收前端登录表单提交的用户名和明文密码，传递到 Service 层
public class EmployeeLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录账号 */
    private String username;

    /** 明文密码（由 Service 层做 MD5 加密后再比对） */
    private String password;

}