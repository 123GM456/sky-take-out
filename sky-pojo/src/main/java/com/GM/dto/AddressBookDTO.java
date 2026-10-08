package com.GM.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 地址簿 DTO，接收前端地址表单数据（新增/编辑共用）。
 */
@Data
public class AddressBookDTO implements Serializable {

    /** 地址 ID（新增时为 null，编辑时必填） */
    private Long id;

    /** 收货人姓名 */
    private String consignee;

    /** 联系电话 */
    private String phone;

    /** 性别：0=女，1=男 */
    private String sex;

    /** 省级行政区编码 */
    private String provinceCode;

    /** 省份名称 */
    private String provinceName;

    /** 市级行政区编码 */
    private String cityCode;

    /** 城市名称 */
    private String cityName;

    /** 区县级行政区编码 */
    private String districtCode;

    /** 区县名称 */
    private String districtName;

    /** 详细地址（门牌号等） */
    private String detail;

    /** 地址标签（如：家、公司、学校） */
    private String label;

    /** 是否默认地址：0=否，1=是 */
    private Integer isDefault;
}