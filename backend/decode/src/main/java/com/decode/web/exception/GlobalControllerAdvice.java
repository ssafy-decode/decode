package com.decode.web.exception;

import com.decode.web.global.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ResponseDto> credentialException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<ResponseDto> expiredJwtException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Expired JWT")
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseDto> handleException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal Server Error")
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
