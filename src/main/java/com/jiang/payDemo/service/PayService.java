package com.jiang.payDemo.service;


import jakarta.servlet.http.HttpServletRequest;

/**
 * @author lfy
 * @Description
 * @create 2022-12-28 22:36
 */
public interface PayService {

    /**
     * 生成支付收银台页
     * @param type
     * @param orderId
     * @return
     */
    String payPage(String type, Long orderId);


    /**
     * 处理支付通知
     * @param request
     * @return
     */
    Object processNotify(HttpServletRequest request, String body);
}
