package com.kanezheng.app.jobmanagement.exception;

import com.kanezheng.app.jobmanagement.controller.schedule.ScheduleController;
import com.kanezheng.app.jobmanagement.response.ApiResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(assignableTypes = ScheduleController.class)
public class CustomExceptionHandler{

    Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public final ErrorResponse handleAllExceptions(Exception ex, WebRequest request) {

        logger.info("handleAllExceptions!!!!");

//        return new ApiResponse(1000, "this is a custom exception!", null);
/*        ApiResponse customResponse = new ApiResponse(1000, "this is a custom exception!", null);
        return new ResponseEntity(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);*/
        return new ErrorResponse(1000, "Not found!");

    }

    @Data
    public static class ErrorResponse {
        private final int code;
        private final String message;
    }
}
