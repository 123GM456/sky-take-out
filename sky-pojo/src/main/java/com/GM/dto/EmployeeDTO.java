package com.GM.dto;

// Lombok：自动生成 getter/setter、toString、equals、hashCode
import lombok.Data;
// JDK：实现序列化接口，支持 DTO 对象跨线程/跨网络传输
import java.io.Serializable;

/**
 * 员工新增 DTO，接收前端添加员工表单提交的数据。
 * 不包含 password —— 新增员工时由 Service 自动填充默认密码（见 PasswordConstant）。
 * 不包含 id、status、createTime 等 —— 由系统自动生成或默认赋值。
 */
@Data
// DTO：接收前端新增员工表单数据（用户名、姓名、手机号、性别、身份证号），传至 Service 层
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录账号（唯一） */
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