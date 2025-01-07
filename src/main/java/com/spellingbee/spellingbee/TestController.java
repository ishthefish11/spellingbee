package com.spellingbee.spellingbee;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public String test(HttpServletRequest http) {
        return "Hello World! Currently accessed by: " + http.getSession().getId();
    }
}
