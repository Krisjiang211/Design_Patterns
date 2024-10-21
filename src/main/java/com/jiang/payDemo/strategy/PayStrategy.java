package com.jiang.payDemo.strategy;

import com.jiang.payDemo.model.OrderInfo;
import jakarta.servlet.http.HttpServletRequest;


import java.util.Map;

/**
 * @author lfy
 * @Description 支付策略
 * @create 2022-12-28 22:39
 */
public interface PayStrategy {

    /**
     * 支持哪种支付
     * @param type
     * @return
     */
    boolean supports(String type);

    /**
     * 为某个订单展示收银台页面
     * @return
     */
    String cashierPage(OrderInfo orderInfo);

    /**
     * 验证签名
     * @param request  原生请求
     * @param body     请求体数据【请求体只能读取一次，所以controller拿到以后都往下传递即可】
     * @return
     */
    boolean checkSign(HttpServletRequest request, String body);


    /**
     * 验签错误处理
     * @return
     */
    Object signError();


    /**
     * 验签通过返回
     * @return
     */
    Object signOk();

    /**
     * 验签成功后处理通知数据： 把通知的所有数据封装指定对象
     * @param request
     * @return
     */
    Map<String,Object> process(HttpServletRequest request,String body);
}
