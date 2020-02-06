package com.kanezheng.app.jobmanagement.response;


import com.kanezheng.app.jobmanagement.controller.schedule.ScheduleController;
import com.kanezheng.app.jobmanagement.exception.CustomExceptionHandler;
import com.kanezheng.app.jobmanagement.service.schedule.ScheduleServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice {

    Logger logger = LoggerFactory.getLogger(CustomResponseBodyAdvice.class);

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        logger.info("CustomResponseBodyAdvice_beforeBodyWrite: object = {}, methodParameter = {}", o.toString(), methodParameter);

        ApiResponse customResponse = new ApiResponse(200, "success", o);
        return customResponse;
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        Boolean bSupport = methodParameter.getParameterType() != CustomExceptionHandler.ErrorResponse.class;

        logger.info("CustomResponseBodyAdvice_supports:{}, methodParameter:{}", bSupport, methodParameter);

        return  bSupport;
    }
}