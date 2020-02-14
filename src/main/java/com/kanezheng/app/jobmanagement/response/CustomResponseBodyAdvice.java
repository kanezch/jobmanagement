package com.kanezheng.app.jobmanagement.response;

import com.kanezheng.app.jobmanagement.controller.schedule.EmailNotifyJobController;
import com.kanezheng.app.jobmanagement.exception.CustomExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(assignableTypes = EmailNotifyJobController.class)
public class CustomResponseBodyAdvice implements ResponseBodyAdvice {

    Logger logger = LoggerFactory.getLogger(CustomResponseBodyAdvice.class);

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        ApiResponse customResponse = new ApiResponse(200, "success", object);
        return customResponse;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        Boolean bSupport = methodParameter.getParameterType() != CustomExceptionHandler.ErrorResponse.class;

        logger.info("CustomResponseBodyAdvice_supports:{}, methodParameter:{}", bSupport, methodParameter);

        return  bSupport;
    }
}