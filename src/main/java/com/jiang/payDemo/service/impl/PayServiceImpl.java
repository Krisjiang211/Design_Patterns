package com.jiang.payDemo.service.impl;
import java.math.BigDecimal;
import java.util.Date;

import com.jiang.payDemo.model.OrderInfo;
import com.jiang.payDemo.service.PayService;
import com.jiang.payDemo.strategy.PayStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Map;

/**
 * @author lfy
 * @Description 支付上下文引用支付策略； 这个上下文也是模板类；定义好算法步骤
 * @create 2022-12-28 22:46
 */
@Service
@Slf4j //模板类
public class PayServiceImpl implements PayService {

    @Autowired
    List<PayStrategy> payStrategies; //注入支付策略

    /**
     * 生成收银台页面
     * @param type
     * @param orderId
     * @return
     */
    @Override
    public String payPage(String type, Long orderId) {
        //1、查询数据库订单
        OrderInfo orderInfo = getOrderInfo(orderId);

        //2、生成支付页
        for (PayStrategy strategy : payStrategies) {
            if(strategy.supports(type)){
                //获取收银台页面
                return strategy.cashierPage(orderInfo);
            }
        }
        //3、如果以上都不支持，打印错误
        return "不支持这种支付方式";
    }


    /**
     * 定义通知处理模板；
     * 微信通知
     * 支付宝通知
     * 1）、验证签名
     * 2）、验证通过改订单为已支付
     * 3）、验证通过给支付宝(success)微信(200状态码json)返回数据
     * 4）、xxx
     * @param request
     * @param body
     * @return
     */
    @Override
    public Object processNotify(HttpServletRequest request, String body) {
        Object result = "不支持此方式";

        //1、判断是那种通知
        String type = getNotifyType(request);
        Map<String, Object> data = null;

        //2、验证签名
        for (PayStrategy strategy : payStrategies) {
            if(strategy.supports(type)){
                //签名校验
                boolean checkSign = strategy.checkSign(request,body);
                if(!checkSign){
                    log.error("签名验证失败，疑似攻击请求");
                    //验签失败返回
                   return strategy.signError();
                }else {
                    log.info("签名验证成功，提取通知数据");
                    //验签成功处理数据
                    data = strategy.process(request,body);
                    //验签成功返回
                    result = strategy.signOk();
                }
            }
        }

        //3、通用的后续处理算法；处理订单数据
        processOrder(data);

        return result;
    }

    /**
     * 处理订单数据
     * @param data
     */
    private void processOrder(Map<String, Object> data) {
        //TODO 把支付成功信息等保存数据库，并修改订单状态，通知库存系统等...
        log.info("订单支付成功，状态修改完成，已通知库存系统，详细数据：{}",data);
    }

    /**
     * 判断通知类型
     * @param request
     * @return
     */
    private String getNotifyType(HttpServletRequest request) {
        String header = request.getHeader("wechatpay-serial");
        if(StringUtils.hasText(header)){
            return "weixin";
        }

        String app_id = request.getParameter("app_id");
        if(StringUtils.hasText(app_id)){
            return "alipay";
        }


        return "unknown";
    }

    public OrderInfo getOrderInfo(Long orderId){
        log.info("查询数据库订单：{}",orderId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setTitle("尚品汇-商城-订单");
        orderInfo.setComment("快点发货");
        orderInfo.setDesc("买了一堆商品");
        orderInfo.setPrice(new BigDecimal("9098.00"));
        orderInfo.setExpireTime(new Date(System.currentTimeMillis()+30*60*1000));

        return orderInfo;
    }
}
