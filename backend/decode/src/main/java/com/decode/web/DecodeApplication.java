package com.decode.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {
        @Server(url = "https://i10a507.p.ssafy.io/decode/", description = "Default Server URL")})
@SpringBootApplication
public class DecodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecodeApplication.class, args);
    }

}
