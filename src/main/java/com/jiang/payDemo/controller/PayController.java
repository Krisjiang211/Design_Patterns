package com.jiang.payDemo.controller;

import com.jiang.payDemo.service.PayService;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lfy
 * @Description
 * @create 2022-12-28 21:33
 */
@Slf4j
@Controller
public class PayController {

    @Autowired
    PayService payService;

    public PayController(){
        //初始化id生成器
        IdGeneratorOptions options = new IdGeneratorOptions();
        YitIdHelper.setIdGenerator(options);
    }

    /**
     * 支付确认页
     * @param model
     * @return
     */
    @GetMapping({"/","/index","/pay.html"})
    public String index(Model model){
        //模拟生成订单id
        long nextId = YitIdHelper.nextId();
        model.addAttribute("orderId",nextId);
        return "index";
    }


    /**
     * 微信、支付宝收银台
     * @param type
     * @param orderId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/pay/order",produces = "text/html")
    public String generatePayPage(@RequestParam("type") String type,
                                  @RequestParam("orderId") Long orderId){

        //来到支付收银台页
        String page = payService.payPage(type,orderId);
        return page;
    }

    @GetMapping("/pay/success.html")
    public String paySuccess(){

        return "success";
    }

}
