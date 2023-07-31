package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.UserService;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class UserController {
    UserService userService;
    LoanService loanService;
    public UserController(UserService userService,LoanService loanService) {
        this.userService = userService;
        this.loanService= loanService;
    }


    @GetMapping("/edit")
    public String updateProfile(Model model){
        String username= SecurityUtil.getSessionUser();
        UserRegistrationDto userRegistrationDto= userService.findByUserName(username);
        List<Loan> loanList=loanService.getAllLoansByUserId(userRegistrationDto.getId());

        // get the total monthly interest
        double totalMonthly= 0;
        for(Loan x: loanList){
            totalMonthly+=x.getAmountBorrowed() *x.getInterestRate() /100/12;
        }

        model.addAttribute("user", userRegistrationDto);
        model.addAttribute("money",0);
        model.addAttribute("bio",null);
        model.addAttribute("loans",
                loanService.getAllLoansByUserId(userRegistrationDto.getId()) );
        model.addAttribute("totalMonthly",String.format("%.2f",totalMonthly) );
        return "UserPages/ShowUser";
    }

}
