package com.decode.web.exception;

import com.decode.web.global.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
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

    @ExceptionHandler(FollowException.class)
    private ResponseEntity<ResponseDto> followException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserException.class)
    private ResponseEntity<ResponseDto> userException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ResponseDto> credentialException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<ResponseDto> expiredJwtException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Expired JWT")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ResponseDto> EntityNotFoundException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseDto> handleException(Exception e) {
        log.error("{}", e.getMessage());
        log.debug("{}", e.getStackTrace());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal Server Error")
                .data("")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
