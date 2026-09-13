package com.sky.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体，对应数据库 employee 表。
 * 用于 MyBatis ORM 映射和业务层传递。
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 登录账号（唯一） */
    private String username;

    /** MD5 加密后的密码 */
    private String password;

    /** 真实姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别：1=男，0=女 */
    private String sex;

    /** 身份证号 */
    private String idNumber;

    /** 状态：1=正常，0=禁用 */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 创建人（employee.id） */
    private Long createUser;

    /** 修改人（employee.id） */
    private Long updateUser;

}