package com.jiang.payDemo.strategy.impl;

import com.jiang.payDemo.model.OrderInfo;
import com.jiang.payDemo.strategy.PayStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author lfy
 * @Description
 * @create 2022-12-29 21:15
 */
@Component
public class BaitiaoStrategy implements PayStrategy {
    @Override
    public boolean supports(String type) {
        return "baitiao".equalsIgnoreCase(type);
    }

    @Override
    public String cashierPage(OrderInfo orderInfo) {
        return "白条页面";
    }

    @Override
    public boolean checkSign(HttpServletRequest request, String body) {
        return true;
    }

    @Override
    public Object signError() {
        return null;
    }

    @Override
    public Object signOk() {
        return null;
    }

    @Override
    public Map<String, Object> process(HttpServletRequest request, String body) {
        return null;
    }
}
