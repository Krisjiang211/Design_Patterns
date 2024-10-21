package com.jiang.payDemo.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lfy
 * @Description
 * @create 2022-12-28 22:40
 */
@Data
public class OrderInfo {
    private Long id; //订单id
    private String title;//订单标题
    private String comment;//备注
    private String desc;//描述
    private BigDecimal price;//金额
    private Date expireTime;//过期时间
}
