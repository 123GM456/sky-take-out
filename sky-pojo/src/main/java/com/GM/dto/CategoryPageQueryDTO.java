package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 分类分页查询参数 DTO，接收前端分类列表请求的查询条件。
 */
@Data
public class CategoryPageQueryDTO implements Serializable {

    /** 页码（从 1 开始） */
    private int page;

    /** 每页显示条数 */
    private int pageSize;

    /** 分类名称（可选，传值时做模糊匹配） */
    private String name;

    /** 分类类型（可选，传值筛选：1=菜品分类，2=套餐分类） */
    private Integer type;
}