package com.jiang.payDemo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lfy
 * @Description
 * @create 2022-12-28 23:20
 */
@ConfigurationProperties(prefix = "app.pay.wechat")
@Data
public class WechatProperties {
    private String merchantId;
    private String merchantSerialNumber;
    private String privateKey;
    private String apiV3Key;
    private String appId;
    private String domain;
    private String notifyUrl;
    private String partnerKey;
}
