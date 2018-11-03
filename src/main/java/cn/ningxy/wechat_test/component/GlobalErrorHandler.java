package cn.ningxy.wechat_test.component;

import cn.ningxy.wechat_test.entity.ResultBody;
import cn.ningxy.wechat_test.exception.GlobalErrorException;
import cn.ningxy.wechat_test.exception.GlobalErrorInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一错误码异常处理
 *
 * @author ningxy
 * @date 2018-10-24 17:16
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(value = GlobalErrorException.class)
    public ResultBody errorHandlerOverJson(GlobalErrorException exception) {
        log.error(exception.toString());
        GlobalErrorInterface errorInfo = exception.getErrorInfo();
        return new ResultBody(errorInfo);
    }

    @ExceptionHandler(value = Exception.class)
    public ResultBody errorHandlerOverJson1(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResultBody(500, exception.getMessage());
    }
}
