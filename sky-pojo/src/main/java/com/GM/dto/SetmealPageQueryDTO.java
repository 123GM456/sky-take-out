package com.GM.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 套餐分页查询 DTO。
 */
@Data
public class SetmealPageQueryDTO implements Serializable {

    /** 页码（从 1 开始） */
    private int page;

    /** 每页条数 */
    private int pageSize;

    /** 套餐名称（模糊搜索） */
    private String name;

    /** 分类 ID（精确筛选） */
    private Integer categoryId;

    /** 状态：1=起售，0=停售 */
    private Integer status;
}