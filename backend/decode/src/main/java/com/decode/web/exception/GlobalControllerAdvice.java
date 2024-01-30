package com.decode.web.exception;

import com.decode.web.global.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(FollowException.class)
    private ResponseEntity<ResponseDto> followException(FollowException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .headers(new HttpHeaders())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserException.class)
    private ResponseEntity<ResponseDto> userException(UserException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .headers(new HttpHeaders())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ResponseDto> credentialException(BadCredentialsException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .headers(new HttpHeaders())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<ResponseDto> expiredJwtException(ExpiredJwtException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Expired JWT")
                .headers(new HttpHeaders())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ResponseDto> EntityNotFoundException(EntityNotFoundException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotEnoughCoinException.class)
    private ResponseEntity<ResponseDto> notEnoughCoinException(NotEnoughCoinException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("코인이 부족합니다")
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseDto> handleException(Exception e) {
        log.error("{}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal Server Error")
                .data("")
                .headers(new HttpHeaders())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
