package com.decode.web.exception;

public class NotEnoughCoinException extends RuntimeException {

    public NotEnoughCoinException(String message) {
        super(message);
    }
}
