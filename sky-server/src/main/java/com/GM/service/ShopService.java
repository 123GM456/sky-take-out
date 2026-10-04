package com.GM.service;

public interface ShopService {

    /**
     * 获取店铺营业状态。
     * @return 店铺营业状态：1=营业，0=打烊
     */
    Integer getStatus();
    /**
     * 设置店铺营业状态。
     * @param status 目标状态：1=营业，0=打烊
     */
    void setStatus(Integer status);

}