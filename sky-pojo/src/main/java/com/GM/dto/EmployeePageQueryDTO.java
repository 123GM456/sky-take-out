package com.GM.dto;

// Lombok：自动生成 getter/setter、toString、equals、hashCode
import lombok.Data;

/**
 * 员工分页查询参数。
 */
// Lombok：自动生成 getter/setter、toString、equals、hashCode、canEqual
@Data
// DTO：接收前端分页查询参数（页码、每页条数、员工姓名），传到 Service 层
public class EmployeePageQueryDTO {

    /** 页码，从 1 开始 */
    private Integer page;

    /** 每页条数 */
    private Integer pageSize;

    /** 员工姓名（模糊查询，可为空） */
    private String name;

}