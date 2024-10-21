package com.jiang.payDemo.strategy.impl;

import com.jiang.payDemo.model.OrderInfo;
import com.jiang.payDemo.strategy.PayStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author lfy
 * @Description
 * @create 2022-12-29 21:17
 */
@Component
public class VisaStrategy implements PayStrategy {
    @Override
    public boolean supports(String type) {
        return "visa".equalsIgnoreCase(type);
    }

    @Override
    public String cashierPage(OrderInfo orderInfo) {
        return "Visa 收银台";
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
