package com.kuaikan.log4j2demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("/")
public class TestController {

    @GetMapping("ok")
    public String ok() {
        return "It's ok!";
    }

    @GetMapping("bad")
    public String bad() {
        long startTime = System.currentTimeMillis();
        try {
            throw new Exception("test bad error");
        } catch (Exception es) {
            log.error("test bad error: {}", es);
        } finally {
            log.info("time bad cost: {}", System.currentTimeMillis() - startTime);
        }
        return "It's bad!";
    }
}
