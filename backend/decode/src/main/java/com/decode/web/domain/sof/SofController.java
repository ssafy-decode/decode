package com.decode.web.domain.sof;

import com.decode.web.domain.sof.dto.SearchDto;
import com.decode.web.domain.sof.dto.SofDto;
import com.decode.web.global.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sof")
@RequiredArgsConstructor
public class SofController {


    private final StackOverflowClient stackOverflowClient;

    @PostMapping("/search")
    public ResponseDto search(@RequestBody SearchDto searchDto){
        return ResponseDto.builder().status(HttpStatus.OK).message("불러오기 성공").build();
    }



}
