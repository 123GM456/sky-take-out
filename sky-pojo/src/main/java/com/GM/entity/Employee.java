package com.GM.entity;

// Lombok：自动生成 getter/setter、toString、equals、hashCode
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// JDK：实现序列化接口，支持 Redis 缓存 / MyBatis 二级缓存 / 网络传输
import java.io.Serializable;
// JDK：Java 8+ 日期时间类型，用于映射数据库的 datetime 字段（create_time、update_time）
import java.time.LocalDateTime;

/**
 * 员工实体，对应数据库 employee 表。
 * 用于 MyBatis ORM 映射和业务层传递。
 */
// Lombok：自动生成 getter/setter、toString、equals、hashCode、canEqual
@Data
// Lombok：生成全参构造器，@Builder 需要它来生成 builder() 方法
@Builder
// Lombok：生成无参构造器，MyBatis ORM 映射时需要无参构造反射创建对象
@NoArgsConstructor
// Lombok：生成全参构造器，@Builder 内部依赖它
@AllArgsConstructor
// 实体：与数据库 employee 表一一对应，MyBatis ORM 映射和业务层传递使用
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

    public void setPassword(String password) {

    }
}