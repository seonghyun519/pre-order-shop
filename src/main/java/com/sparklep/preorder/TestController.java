package com.sparklep.preorder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
    @RequestMapping("/test")
    public String getHello() {
        return "Test Pass";
    }
}
