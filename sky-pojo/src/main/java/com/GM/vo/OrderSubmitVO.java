package com.GM.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提交订单返回视图对象（VO）。
 * <p>下单成功后返回给前端用于跳转支付页，只暴露必要的少量字段。</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 订单 ID */
    private Long id;

    /** 订单号 */
    private String orderNumber;

    /** 订单金额 */
    private BigDecimal orderAmount;

    /** 下单时间 */
    private LocalDateTime orderTime;
}