package com.jiang.payDemo.listener;

import com.jiang.payDemo.service.PayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

/**
 * @author lfy
 * @Description 监听支付成功通知
 * @create 2022-12-29 13:49
 */
@Slf4j
@RestController
public class PayStatusNotifyListener {


    @Autowired
    PayService payService;

    @RequestMapping("/api/payment/notify")
    public Object paystatusCallback(HttpServletRequest request,
                                    @RequestParam Map<String,String> params,
                                    @RequestHeader Map<String,String> headers,
                                    @RequestBody String body){
        log.info("支付成功通知抵达：");
        log.info("请求头：{}",headers);
        log.info("请求参数：{}",params);
        log.info("请求体：{}",body);

        Object notify = payService.processNotify(request,body);


        return notify;
    }

}
