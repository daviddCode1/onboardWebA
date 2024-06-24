package com.OnbaordWeb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientForwardController {

    //all paths redirects to /
    @GetMapping(value = "/**/{path:[^\\.]*}" )
    public  String forward(){

        return "forward:/";
    }
}
