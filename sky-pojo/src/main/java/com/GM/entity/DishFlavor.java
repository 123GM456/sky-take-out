package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 菜品口味实体，与数据库 dish_flavor 表一一对应（ORM 映射）。
 * <p>一个菜品对应多条口味记录，如"辣度：微辣、中辣、特辣"。</p>
 */
@Data
@Builder
@NoArgsConstructor          // 无参构造器（MyBatis ORM 反射创建对象时必需）
@AllArgsConstructor         // 全参构造器（@Builder 内部需要）
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 所属菜品 ID */
    private Long dishId;

    /** 口味名（如"辣度""甜度""温度"） */
    private String name;

    /** 口味可选项，JSON 数组格式（如 ["微辣","中辣","特辣"]） */
    private String value;
}