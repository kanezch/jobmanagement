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


//@ControllerAdvice(assignableTypes = ScheduleController.class)
public class CustomExceptionHandler{

    Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);


    @ExceptionHandler(ScheduleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleScheduleNotFoundException(final ScheduleNotFoundException ex) {
        logger.error("Schedule not found exceptioin", ex);
        return new ErrorResponse(1001, "The schedule was not found");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final ErrorResponse handleAllExceptions(Exception ex, WebRequest request) {

        logger.info("CustomExceptionHandler_handleAllExceptions.");

        return new ErrorResponse(1000, "There is an exception.");
    }

    @Data
    public static class ErrorResponse {
        private final int code;
        private final String message;
    }
}
