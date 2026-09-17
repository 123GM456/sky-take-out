package com.GM.dto;

import lombok.Data;

/**
 * 员工分页查询参数 DTO，接收前端分页请求的查询条件。
 * <p>name 为空时查全部，非空时按姓名模糊匹配。</p>
 */
@Data
public class EmployeePageQueryDTO {

    /** 页码（从 1 开始） */
    private Integer page;

    /** 每页显示条数 */
    private Integer pageSize;

    /** 员工姓名（可选，传值时做模糊查询 LIKE %name%） */
    private String name;
}