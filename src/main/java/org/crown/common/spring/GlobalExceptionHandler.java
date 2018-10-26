package org.crown.common.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.api.model.ErrorCode;
import org.crown.common.exception.ApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author Caratacus
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 自定义 REST 业务异常
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public void handleBadRequest(HttpServletRequest request, HttpServletResponse response, ApiException exception) {
        ErrorCode code = exception.getErrorCode();
        //TDDO
        //ApiUtils.sendRestFail(request, response, code);
        if (code.getHttpCode() < HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            log.info("Info: code: {} ,httpCode: {} ,msg: {}", code.getError(), code.getHttpCode(), code.getMsg());
        } else {
            log.warn("Warn: code: {} ,httpCode: {} ,msg: {}", code.getError(), code.getHttpCode(), code.getMsg());
        }
    }

}
