package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 菜品分页查询参数 DTO，接收前端菜品列表请求的查询条件。
 */
@Data
public class DishPageQueryDTO implements Serializable {

    /** 页码（从 1 开始） */
    private int page;

    /** 每页显示条数 */
    private int pageSize;

    /** 菜品名称（可选，传值时做模糊匹配） */
    private String name;

    /** 分类 ID（可选，传值时按分类筛选） */
    private Integer categoryId;

    /** 状态（可选，传值筛选：1=起售，0=停售） */
    private Integer status;
}