package cn.com.yto.reywong.tool.springboot.test.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangrui on 2017/7/31.
 */
@SpringBootApplication
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

}
