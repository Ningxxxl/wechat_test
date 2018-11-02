package cn.ningxy.wechat_test.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ningxy
 * @date 2018-11-02 16:11
 */
@Component
public class WeChatUtil {

    private static String token;

    /**
     * 1）将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return timestamp, nonce, token加密后的字符串与signature是否一致
     */
    public static boolean check(String signature, String timestamp, String nonce) {
        ArrayList<String> list = new ArrayList<>(3);
        list.add(timestamp);
        list.add(nonce);
        list.add(token);

        Collections.sort(list);
        String str = String.join("", list);
        str = SHA1Util.getSha1(str);

        return signature.equals(str);
    }

    @Value("${wechat.core.token}")
    public void setToken(String token) {
        WeChatUtil.token = token;
    }
}
