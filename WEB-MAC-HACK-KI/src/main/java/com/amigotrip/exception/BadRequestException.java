package com.amigotrip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Naver on 2017. 11. 26..
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You sended bad request")
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }
    public BadRequestException(String s) {
        super(s);
    }
    public BadRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public BadRequestException(Throwable throwable) {
        super(throwable);
    }
}
