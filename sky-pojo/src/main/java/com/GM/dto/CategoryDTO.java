package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 分类 DTO，接收前端分类表单数据（新增/编辑共用）。
 */
@Data
public class CategoryDTO implements Serializable {

    /** 分类 ID（新增时为 null，编辑时必填） */
    private Long id;

    /** 分类类型：1=菜品分类，2=套餐分类 */
    private Integer type;

    /** 分类名称 */
    private String name;

    /** 排序号（数字越小越靠前） */
    private Integer sort;
}