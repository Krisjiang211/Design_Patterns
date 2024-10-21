package com.jiang.payDemo.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jiang.payDemo.config.properties.AlipayProperties;
import com.jiang.payDemo.model.OrderInfo;
import com.jiang.payDemo.strategy.PayStrategy;
import com.jiang.payDemo.util.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lfy
 * @Description 支付宝
 * @create 2022-12-28 22:40
 */
@Slf4j
@Component
public class AlipayStrategy implements PayStrategy {

    @Autowired
    AlipayProperties alipayProperties;

    @Autowired
    AlipayClient alipayClient;

    @Override
    public String cashierPage(OrderInfo orderInfo) {
        //1、创建一个 AlipayClient

        //2、创建一个支付请求
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        //3、设置参数
        alipayRequest.setReturnUrl(alipayProperties.getReturn_url()); //同步回调：支付成功以后，浏览器要跳转到的页面地址
        alipayRequest.setNotifyUrl(alipayProperties.getNotify_url()); //通知回调：支付成功以后，支付消息会通知给这个地址


        //商户订单号（对外交易号）
        String outTradeNo = orderInfo.getId().toString();
        //付款金额
        BigDecimal totalAmount = orderInfo.getPrice();
        //订单名称
        String orderName = "尚品汇-订单-"+outTradeNo;
        //商品描述
        String tradeBody = orderInfo.getDesc();

        //详细：https://opendocs.alipay.com/open/028r8t?scene=22
        //业务参数
        Map<String,Object> bizContent = new HashMap<>();
        bizContent.put("out_trade_no",outTradeNo);
        bizContent.put("total_amount",totalAmount);
        bizContent.put("subject",orderName);
        bizContent.put("body",tradeBody);
        bizContent.put("product_code","FAST_INSTANT_TRADE_PAY");
        //自动关单
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderInfo.getExpireTime());
        bizContent.put("time_expire",date);
        alipayRequest.setBizContent(JSON.toJSONString(bizContent));


        //生成支付页面
        String page = null;
        try {
            page = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return page;
    }

    @Override
    public boolean checkSign(HttpServletRequest request, String body) {
        Map<String, String> params = HttpUtils.getParameterMap(request);
        log.info("支付宝通知验证签名...");
        //验证签名
        try {
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params,
                    alipayProperties.getAlipay_public_key(), alipayProperties.getCharset(),
                    alipayProperties.getSign_type());
            return signVerified;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object signError() {
        return "error";
    }

    @Override
    public Map<String,Object> process(HttpServletRequest request,String body) {
        Map<String, String> map = HttpUtils.getParameterMap(request);
        String json = JSON.toJSONString(map);
        Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        return data;
    }

    @Override
    public Object signOk() {
        //支付宝要求成功返回 success 字符串
        return "success";
    }


    @Override  //对新增开放，对修改关闭
    public boolean supports(String type) {
        return "alipay".equalsIgnoreCase(type);
    }
}
