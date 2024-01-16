package com.decode.web.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
public class ResponseDto {
  private HttpStatus status;
  private String message;
  private Object data;


  public ResponseDto() {

  }
}