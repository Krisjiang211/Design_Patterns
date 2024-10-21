package com.jiang.payDemo.strategy.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jiang.payDemo.config.properties.WechatProperties;
import com.jiang.payDemo.model.OrderInfo;
import com.jiang.payDemo.strategy.PayStrategy;
import com.jiang.payDemo.util.HttpUtils;
import com.jiang.payDemo.util.WechatPay2ValidatorForRequest;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lfy
 * @Description 微信支付
 * @create 2022-12-28 22:40
 */
@Slf4j
@Component
public class WeixinPayStrategy implements PayStrategy {

    @Autowired
    private HttpClient httpClientWithNoSign; //无需应答签名
    @Autowired
    private WechatProperties wechatProperties;
    @Autowired
    private Verifier verifier;

    @Override
    public String cashierPage(OrderInfo orderInfo) {
        //调用统一下单API
        HttpPost httpPost = new HttpPost(wechatProperties.getDomain().concat("/v3/pay/transactions/native"));

        // 请求body参数
        Map paramsMap = new HashMap();
        paramsMap.put("appid", wechatProperties.getAppId());
        paramsMap.put("mchid", wechatProperties.getMerchantId());
        paramsMap.put("description", orderInfo.getTitle());
        paramsMap.put("out_trade_no", orderInfo.getId().toString());
        paramsMap.put("notify_url", wechatProperties.getNotifyUrl());

        Map amountMap = new HashMap();
        //注意：这里的金额是以分为单位
//        amountMap.put("total", orderInfo.getPrice().multiply(new BigDecimal("100")).intValue());
        amountMap.put("total", 1); //开发测试期间每次支付1分钱
        amountMap.put("currency", "CNY");

        paramsMap.put("amount", amountMap);

        //将参数转换成json字符串
        String jsonParams = JSON.toJSONString(paramsMap);
        log.info("微信支付：请求参数 ===> {}" + jsonParams);

        StringEntity entity = new StringEntity(jsonParams,"utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");


        String bodyAsString = "";
        try {
            //完成签名并执行请求
            CloseableHttpResponse response = (CloseableHttpResponse) httpClientWithNoSign.execute(httpPost);
            bodyAsString = EntityUtils.toString(response.getEntity());//响应体
            int statusCode = response.getStatusLine().getStatusCode();//响应状态码
            if (statusCode == 200) { //处理成功
                log.info("成功, 返回结果 = " + bodyAsString);
            } else if (statusCode == 204) { //处理成功，无返回Body
                log.info("成功");
            } else {
                log.info("Native下单失败,响应码 = " + statusCode+ ",返回结果 = " + bodyAsString);
                throw new IOException("request failed");
            }

            //响应结果
            JSONObject resultMap = JSON.parseObject(bodyAsString);
            //codeUrl是一个连接地址： weixin://wxpay/bizpayurl?pr=MWkSYAwzz
            //我们需要为这个连接地址生成一个二维码返回给浏览器
            String codeUrl = resultMap.get("code_url").toString();

            //生成图片二维码 base64 串
            String qrCode = createQRCode(codeUrl, 400, 400);
            return "<center><img src='"+qrCode+"'></center>";
        }catch (Exception e){
            log.error("微信收银台页面生成失败：{}",e);
        }
        return bodyAsString;
    }

    @Override
    public boolean checkSign(HttpServletRequest request, String body) {
        try {

            //处理通知参数
            JSONObject jsonObject = JSON.parseObject(body);
            String requestId = (String)jsonObject.get("id");
            //签名的验证
            WechatPay2ValidatorForRequest wechatPay2ValidatorForRequest
                    = new WechatPay2ValidatorForRequest(verifier, requestId, body);
            boolean validate = wechatPay2ValidatorForRequest.validate(request);
            log.info("微信通知验证签名：{}",validate);
            return validate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object signError() {
        //微信验签失败 返回 500状态码就行。  我们用异常机制快速构造错误返回
        throw new RuntimeException("签名验证失败");
    }

    @Override
    public Map<String,Object> process(HttpServletRequest request,String body) {

        //微信验签成功  返回正确数据；
        Map<String, Object> map = JSON.parseObject(body, new TypeReference<Map<String, Object>>() {
        });
        log.info("支付成功数据详情：{}",map);
        return map;
    }

    @Override
    public Object signOk() {
        Map<String,Object> resp = new HashMap<>();
        resp.put("code", "SUCCESS");
        resp.put("message", "成功");
        return resp;
    }


    @Override
    public boolean supports(String type) {
        return "weixin".equalsIgnoreCase(type);
    }

    /**
     * 生成二维码
     * @param content
     * @param width
     * @param height
     * @return
     */
    private String createQRCode(String content, int width, int height){
        //1、生成二维码
        BufferedImage image = QrCodeUtil.generate(content, width, height);

        String png = "data:image/png;base64,"+ImgUtil.toBase64(image, "png");

        return png;
    }
}
