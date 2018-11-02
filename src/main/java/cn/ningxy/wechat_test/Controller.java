package cn.ningxy.wechat_test;

import cn.ningxy.wechat_test.util.SHA1Util;
import cn.ningxy.wechat_test.util.WeChatUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ningxy
 * @date 2018-10-31 16:48
 */
@RestController
@RequestMapping("/")
public class Controller {
    /**
     * 验证请求是否来自微信服务器
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return 确认此次GET请求来自微信服务器，原样返回echostr参数内容
     */
    @GetMapping("/check")
    public String test(String signature, String timestamp, String nonce, String echostr) {
        if (WeChatUtil.check(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return null;
        }
    }
}
