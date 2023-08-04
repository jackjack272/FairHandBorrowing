package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.security.SecurityUtil;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.ProfileTypeService;
import com.example.fairhandborrowing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class AuthenticationController {
    //    https://www.baeldung.com/get-user-in-spring-security
//    https://stackoverflow.com/questions/61167576/securitycontextholder-import-not-working-in-spring-boot-application

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileTypeService profileTypeService;

    @Autowired
    private LoanService loanService;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            UserEntity dto= userService.findByUserName(username);

            double money=0;
            double monthlyInterest=0;
            int months=0;
            int current_date= new Date().getMonth();
            List<Loan> late_loans=new ArrayList<Loan>();
            List<Loan> onTime_loans=new ArrayList<Loan>();
            for(Loan x:  loanService.getAllLoansByUserId(dto.getUserId())){
                money+=x.getAmountBorrowed();
                monthlyInterest+=x.getInterestRate()/100/12*x.getAmountBorrowed();

                if (x.getCreatedOn().getMonth()+ x.getMonthsToPay() >12 ){
                    months= x.getCreatedOn().getMonth()+ x.getMonthsToPay() %12;
                }

                if(months > current_date){
                    // late
                    late_loans.add(x);
                }else{
                    onTime_loans.add(x);
                }
            }
            // get list of late loans.

            model.addAttribute("user",dto);
            model.addAttribute("money",money );
            model.addAttribute("monthlyInterest",monthlyInterest );
            model.addAttribute("late_loans",late_loans );
            model.addAttribute("ontime_loans",onTime_loans );

//            int[] xx = {1, 3, 4};
//            model.addAttribute("test",xx );
        }
        return "UserPages/ShowUser";
    }

    @GetMapping("/profileform")
    public String makeProfile(){


        return "Registration/Profile";

    }


    //todo:registration
    @GetMapping("/register")
    public String getRegistrationPage(Model model){
        model.addAttribute("user",new UserRegistrationDto());
        return "Registration/Register";
    }

    @ModelAttribute("profileTypes")
    public List<String> getProfileTypes() {
        return profileTypeService.getProfileTypes();
    }

    @PostMapping("/register")
    public String saveUser(Model model,
                           @ModelAttribute("UserRegistrationDto")UserRegistrationDto dto,
                           BindingResult result) {

            LOGGER.info("Registration DTO: " + dto);

            if (userService.findByUserName(dto.getUserName()) != null) {
                model.addAttribute("failed","username already taken.");
                return "redirect:/register?fail";
            } else if (userService.findByEmail(dto.getUserName()) != null) {
                model.addAttribute("failed", "email already taken.");
                return "redirect:/register?fail";
            }

            if(result.hasErrors()) {
                model.addAttribute("user", dto);
                return "Registration/Register";
            }

            Optional<UserRegistrationDto> user = userService.saveUser(dto);

            if(user.isPresent()) {
                LOGGER.info("Registration success: " + user.get().getUserName());
            }

            return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        String username = SecurityUtil.getSessionUser();

        if(username == null) {
            model.addAttribute("user", new UserRegistrationDto());
            return "Registration/Login";
        }

        return "redirect:/home";
    }

}
