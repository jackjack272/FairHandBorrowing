package com.example.fairhandborrowing.Controller;

import com.example.fairhandborrowing.DTO.LoanDto;
import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.Loan;
import com.example.fairhandborrowing.Service.LoanService;
import com.example.fairhandborrowing.Service.UserService;
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
        List<LoanDto> loanList=loanService.getAllLoansByUserId(userRegistrationDto.getId());

        // get the total monthly interest
        double totalMonthly= 0;
        for(LoanDto x: loanList){
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


    @GetMapping("/edit_user")
    public String getProfile(Model model){
        String username= SecurityUtil.getSessionUser();
        UserRegistrationDto userRegistrationDto= userService.findByUserName(username);
        model.addAttribute("the_user",userRegistrationDto);
        model.addAttribute("user",new UserRegistrationDto());
        return "UserPages/EditUserPage";
    }

    @PostMapping("/edit_profile")
    public String updateUser(@ModelAttribute("UserRegistrationDto") UserRegistrationDto dto){
        String username= SecurityUtil.getSessionUser();
        UserRegistrationDto userRegistrationDto= userService.findByUserName(username);
        userRegistrationDto.setEmail(dto.getEmail());
        userRegistrationDto.setLastName(dto.getLastName());
        userRegistrationDto.setFirstName(dto.getFirstName());
//        userService.updateUser(userRegistrationDto);
        // update just saves a new user without password ...

        return "redirect:/edit";
    }

}
