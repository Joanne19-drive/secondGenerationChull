package com.minji.underground.exception;

import com.minji.underground.slack.SlackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final SlackService slackService;

    public GlobalExceptionHandler(SlackService slackService) {
        this.slackService = slackService;
    }

    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity handlerCustomException(CustomException ex) {
        slackService.sendMessage(ex.getErrorCode().getMessage());
        return new ResponseEntity(
                ex.getErrorCode().getMessage(),
                HttpStatus.valueOf(ex.getErrorCode().getStatusCode())
        );
    }
}
