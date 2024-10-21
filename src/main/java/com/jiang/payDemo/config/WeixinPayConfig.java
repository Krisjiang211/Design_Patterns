package com.jiang.payDemo.config;

import com.jiang.payDemo.config.properties.WechatProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

/**
 * @author lfy
 * @Description
 * @create 2022-12-28 23:20
 */
@Slf4j
@EnableConfigurationProperties(WechatProperties.class)
@Configuration
public class WeixinPayConfig {


    @Autowired
    WechatProperties wechatProperties;
    /**
     * 给容器中加入WechatPay的HttpClient,虽然它是WechatPay的,
     * 但可以用它给任何外部发请求,因为它只对发给WechatPay的请求做处理而不对发给别的的请求做处理.
     */
    /**
     * 获取http请求对象
     * @param verifier
     * @return
     */
    @Bean(name = "wxPayClient")
    public CloseableHttpClient getWxPayClient(ScheduledUpdateCertificatesVerifier verifier){
        log.info("获取httpClient");

        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(wechatProperties.getPrivateKey());

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(wechatProperties.getMerchantId(), wechatProperties.getMerchantSerialNumber(), privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();

        return httpClient;
    }

    /**
     * 获取签名验证器
     * @return
     */
    @Bean
    public ScheduledUpdateCertificatesVerifier getVerifier(){
        log.info("获取签名验证器");
        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(wechatProperties.getPrivateKey());
        //私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(wechatProperties.getMerchantSerialNumber(),
                privateKey);
        //身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(wechatProperties.getMerchantId(), privateKeySigner);
        // 使用定时更新的签名验证器，不需要传入证书
        ScheduledUpdateCertificatesVerifier verifier = new ScheduledUpdateCertificatesVerifier(
                wechatPay2Credentials,
                wechatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        return verifier;
    }

    /**
     * 获取商户的私钥文件
     * @param filename
     * @return
     */
    private PrivateKey getPrivateKey(String filename){

        try(InputStream stream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return PemUtil.loadPrivateKey(stream);
        } catch (Exception e) {
            throw new RuntimeException("私钥文件不存在", e);
        }
    }
}
