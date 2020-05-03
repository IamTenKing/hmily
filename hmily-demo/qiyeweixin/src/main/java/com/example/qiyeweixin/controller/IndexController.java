package com.example.qiyeweixin.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jt
 * @date 2019-6-22
 */
@Controller
public class IndexController {

    @GetMapping("/test")
    public String test(HttpServletRequest request){

        return "index";
    }


    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","jinting");
        return "index";
    }
}
