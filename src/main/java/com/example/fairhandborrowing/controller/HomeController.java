package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.UserService;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CollateralService collateralService;

    @Autowired
    private LoanService loanService;

    @GetMapping("/")
    public String getIndex() {
        return "redirect:/login";
    }
    @GetMapping("/home")
    public String getHome(Model model) {
        UserRegistrationDto user;
        String username = SecurityUtil.getSessionUser();

        LOGGER.info("user" + username);

        user = userService.findByUserName(username);
        model.addAttribute("user", user);
        if(user.getProfileType().equalsIgnoreCase("BORROWER")) {
            model.addAttribute("userType", "borrower");
            //TODO fetch collateral of borrower
            //TODO fetch loans of borrower
            List<CollateralDto> collaterals = collateralService.findAllCollaterals();
            List<LoanDto> loanDtos = loanService.getAllLoansByUserId(user.getId());
            model.addAttribute("collaterals", collaterals);
            model.addAttribute("loans", loanDtos);
            return "home/borrower";
        }

        return "home/borrower";
    }

}
