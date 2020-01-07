package com.kangjun.annotation;

import com.kangjun.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @AccessLimit(maxCount = 5, seconds = 5)
    @PostMapping("/accessLimit")
    public ServerResponse accessLimit() {
        return ServerResponse.success("accessLimit: success");
    }

}
