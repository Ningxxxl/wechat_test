package cn.ningxy.wechat_test.business.controller;

import cn.ningxy.wechat_test.business.enums.WechatEnum;
import cn.ningxy.wechat_test.entity.ResultBody;
import cn.ningxy.wechat_test.exception.GlobalErrorException;
import cn.ningxy.wechat_test.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ningxy
 * @date 2018-10-31 16:48
 */
@RestController
@RequestMapping("/")
public class Controller {

    @Value("${wechat.core.appId}")
    private String appId;

    @Value("${wechat.core.appSecret}")
    private String appSecret;

    /**
     * 验证请求是否来自微信服务器
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return 确认此次GET请求来自微信服务器，原样返回echostr参数内容
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String test(String signature, String timestamp, String nonce, String echostr) {
        if (WeChatUtil.check(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ResultBody result() {
        return ResultBody.generateSuccessResult("跳转页");
    }

    /**
     * 网页微信授权登录接口
     * @return
     */
    @RequestMapping(value = "/login")
    public void wxLogin(String url, HttpServletResponse response) throws Exception {
        System.out.println("授权登录url:"+url);
        String urls = SnsAPI.connectOauth2Authorize(appId, url, true, "STATE");
        System.out.println(">>> " + urls);
        response.sendRedirect(urls);
    }

    @RequestMapping(value = "/userInfo")
    public ResultBody goToIndex(HttpServletRequest request) throws IOException, GlobalErrorException {
        String code = request.getParameter("code");
        if(code==null||"".equals(code)){
            throw new GlobalErrorException(WechatEnum.CODE_NULL);
        }

        SnsToken snsToken = SnsAPI.oauth2AccessToken(appId, appSecret, code);
        String errcode = snsToken.getErrcode();
        if(errcode!=null&&!"".equals(errcode)){
            throw new GlobalErrorException(WechatEnum.NO_RESPONSE);
        }
        User user = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN",1);
        String errcode1 = user.getErrcode();
        if(errcode1!=null&&!"".equals(errcode1)){
            throw new GlobalErrorException(WechatEnum.SERVER_ERR);
        }
        System.err.println(snsToken);
        return ResultBody.generateSuccessResult(user);
    }

}
