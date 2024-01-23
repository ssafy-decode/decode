package com.decode.web.global.utils.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException authException) throws IOException, ServletException {
        res.setCharacterEncoding("UTF-8");
        res.sendError(401, "잘못된 접근입니다.");


    }

}
