package com.example.fairhandborrowing.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WorkController {

    @GetMapping("/")
    public String here(){
        return "here";
    }

}
