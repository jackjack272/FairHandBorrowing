package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.UserService;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class EditUserController {
    UserService userService;
    LoanService loanService;
    public EditUserController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService= loanService;
    }

    @GetMapping("/edit")
    public String updateProfile(Model model){
        String username= SecurityUtil.getSessionUser();
        UserRegistrationDto dto= UserMapper.mapToDto(userService.findByUserName(username));
        model.addAttribute("user" ,dto);

        return "UserPages/EditUser";
    }

    @PostMapping("/edit")
    public String ss(
            Model model,
            @ModelAttribute("UserEntity")UserRegistrationDto user){
        model.addAttribute("x", user.getUserName());
        userService.updateUser(UserMapper.mapToModel(user));

        return "redirect:/profile";
    }

}
