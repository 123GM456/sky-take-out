package com.GM.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地址簿实体，与数据库 address_book 表一一对应（ORM 映射）。
 * <p>存储用户的收货地址，支持多地址管理，可设置默认地址。</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户 ID（关联 user 表） */
    private Long userId;

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

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}