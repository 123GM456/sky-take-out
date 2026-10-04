package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体，与数据库 user 表一一对应（ORM 映射）。
 * <p>存储微信小程序用户信息，通过 openid 唯一标识用户身份。</p>
 */
@Data               // 自动生成 getter 和 setter 方法
@Builder            // 自动生成 builder 方法，用于创建对象实例
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 微信用户唯一标识（同一小程序下固定不变，用于自动登录） */
    private String openid;

    /** 用户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别：0=女，1=男 */
    private String sex;

    /** 身份证号 */
    private String idNumber;

    /** 头像URL */
    private String avatar;

    /** 注册时间 */
    private LocalDateTime createTime;
}