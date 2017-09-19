package cn.com.yto.reywong.tool.springboot.test.controller;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/welcomeController")
public class WelcomeController extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WelcomeController.class);
    }
    @RequestMapping(value = "/welcome")
    public String toWelcome(Model model) {
        model.addAttribute("message", "welcome to springboot world");
        return "welcome";
    }

}
