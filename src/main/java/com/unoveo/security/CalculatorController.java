package com.unoveo.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class CalculatorController {
    @GetMapping("/calccomp")
    public void calc(){

        System.out.println("calc");
        return ;
    }

//    @RequestMapping("/calc")
//    public void index(){
//        System.out.println("calc");
//        return ;
//    }
}
