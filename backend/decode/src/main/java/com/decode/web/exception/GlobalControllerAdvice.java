package com.decode.web.exception;

import com.decode.web.global.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import javax.security.auth.login.CredentialException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(CustomLoginException.class)
    private ResponseEntity<ResponseDto> customLoginException(CustomLoginException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FollowException.class)
    private ResponseEntity<ResponseDto> followException(FollowException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    private ResponseEntity<ResponseDto> userException(UserException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ResponseDto> badCredentialException(BadCredentialsException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CredentialException.class)
    private ResponseEntity<ResponseDto> credentialException(CredentialException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<ResponseDto> expiredJwtException(ExpiredJwtException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Expired JWT")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ResponseDto> entityNotFoundException(EntityNotFoundException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughCoinException.class)
    private ResponseEntity<ResponseDto> notEnoughCoinException(NotEnoughCoinException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("코인이 부족합니다")
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughCountException.class)
    private ResponseEntity<ResponseDto> notEnoughCountException(NotEnoughCountException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ResponseDto> methodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidWriterException.class)
    private ResponseEntity<ResponseDto> invalidWriterException(
            InvalidWriterException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<ResponseDto> badRequestException(BadRequestException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<ResponseDto> noSuchElementException(NoSuchElementException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("DB 조회 불가")
                .data("")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseDto> handleException(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(ResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .data("")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
