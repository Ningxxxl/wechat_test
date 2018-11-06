package cn.ningxy.wechat_test.business.enums;

import cn.ningxy.wechat_test.exception.GlobalErrorInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ningxy
 * @date 2018-11-05 16:57
 */
@AllArgsConstructor
@Getter
public enum WechatEnum implements GlobalErrorInterface {


    CODE_NULL(10001, "code为空"),

    NO_RESPONSE(10002, "微信未响应"),

    SERVER_ERR(10003, "微信获取信息出错")
    ;

    @Setter
    private Integer code;

    @Setter
    private String message;


}
