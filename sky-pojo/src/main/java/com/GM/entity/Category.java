package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类实体，与数据库 category 表一一对应（ORM 映射）。
 */
@Data
@Builder
@NoArgsConstructor          // 无参构造器（MyBatis ORM 反射创建对象时必需）
@AllArgsConstructor         // 全参构造器（@Builder 内部需要）
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 分类类型：1=菜品分类，2=套餐分类 */
    private Integer type;

    /** 分类名称 */
    private String name;

    /** 排序号（数字越小越靠前） */
    private Integer sort;

    /** 状态：1=启用，0=禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近修改时间 */
    private LocalDateTime updateTime;

    /** 创建人 ID */
    private Long createUser;

    /** 最近修改人 ID */
    private Long updateUser;
}