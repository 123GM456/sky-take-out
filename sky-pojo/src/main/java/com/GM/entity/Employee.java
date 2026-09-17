package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体，与数据库 employee 表一一对应（ORM 映射）。
 * <p>MyBatis 查询结果会自动映射为此类型字段，Service 层在业务逻辑中传参也使用此类型。</p>
 */
@Data
@Builder                     // Builder 模式构造：Employee.builder().id(1).build()
@NoArgsConstructor           // 无参构造器（MyBatis ORM 反射创建对象时必需）
@AllArgsConstructor          // 全参构造器（@Builder 内部需要）
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 登录账号（唯一，数据库 uk_username 唯一索引约束） */
    private String username;

    /** 密码（MD5 加密后存储） */
    private String password;

    /** 员工真实姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别：1=男，0=女 */
    private String sex;

    /** 身份证号 */
    private String idNumber;

    /** 状态：1=正常，0=禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近修改时间 */
    private LocalDateTime updateTime;

    /** 创建人 ID（关联 employee.id） */
    private Long createUser;

    /** 最近修改人 ID（关联 employee.id） */
    private Long updateUser;
}