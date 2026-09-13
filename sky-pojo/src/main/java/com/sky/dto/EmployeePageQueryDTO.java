package com.sky.dto;

import lombok.Data;

/**
 * 员工分页查询参数。
 */
@Data
public class EmployeePageQueryDTO {

    /** 页码，从 1 开始 */
    private Integer page;

    /** 每页条数 */
    private Integer pageSize;

    /** 员工姓名（模糊查询，可为空） */
    private String name;

}
