package com.kanezheng.app.jobmanagement.response;


import com.kanezheng.app.jobmanagement.controller.schedule.ScheduleController;
import com.kanezheng.app.jobmanagement.exception.CustomExceptionHandler;
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

@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        System.out.println("TestResponseBodyAdvice==>beforeBodyWrite:" + o.toString() + ","
                + methodParameter);

        ApiResponse customResponse = new ApiResponse(200, "success", o);
        return customResponse;
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        System.out.println("In supports() method of " + getClass().getSimpleName());

        System.out.println("methodParameter.getParameterType(): " + methodParameter.toString());

        Boolean aBoolean = methodParameter.getParameterType() != CustomExceptionHandler.ErrorResponse.class;

        System.out.println("aBoolean " + aBoolean);
        return  aBoolean;
    }
}