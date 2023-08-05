package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.security.SecurityUtil;
import com.example.fairhandborrowing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String xx(
            Model model
    ){
        String username= SecurityUtil.getSessionUser();
        UserRegistrationDto dto= UserMapper.mapToDto( userService.findByUserName(username));
        if(!dto.getProfileType().equals("ADMIN")){
//            return "redirect:/error";
        }

        model.addAttribute("x",(dto.getProfileType()));

        return "work";
    }

}
