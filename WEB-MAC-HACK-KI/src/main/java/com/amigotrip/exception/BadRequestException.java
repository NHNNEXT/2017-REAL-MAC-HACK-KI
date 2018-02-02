package com.amigotrip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

/**
 * Created by Naver on 2017. 11. 26..
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException() {
        super();
    }
    public BadRequestException(String s) {
        super(s);
        this.message = s;
    }

    public BadRequestException(String s, Throwable throwable) {
        super(s, throwable);
        this.message = s;
    }
    public BadRequestException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
