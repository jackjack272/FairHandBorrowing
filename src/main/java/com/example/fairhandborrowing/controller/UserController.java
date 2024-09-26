package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.UserService;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.rmi.server.ExportException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
public class UserController {
    UserService userService;
    LoanService loanService;
    public UserController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService= loanService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/profilez")
    public String showProfile1(Model model){
        String username = SecurityUtil.getSessionUser();

        UserRegistrationDto user= UserMapper.mapToDto( userService.findByUserName(username));
        List<Loan> all_loans= ( loanService.getAllLoansByUserId(user.getId()) );

        double money=0;
        double monthly_interest=0;
        List<Loan> ontime_loans=null;
        List<Loan> late_loans=null;

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.ssss");
        int year, month, day, expiery, dueMonth;
        Date currentDate= new Date();
        String msg="";
        for(Loan x: all_loans){
            if (x.getAmountBorrowed() ==null) {
                continue;
            }
            money+=x.getAmountBorrowed();
            monthly_interest+= (x.getInterestRate()/100/12*x.getAmountBorrowed());

            // 5 months to pay +update=created on =8
            // 5+8 %12
            // get loan year +1
            //get current date
            // if

            year=Integer.parseInt( x.getCreatedOn().toString().split(" ")[0].split("-")[0]);
            month=Integer.parseInt( x.getCreatedOn().toString().split(" ")[0].split("-")[1]);
            day=Integer.parseInt( x.getCreatedOn().toString().split(" ")[0].split("-")[2]);

            dueMonth=0;
            dueMonth=x.getMonthsToPay()  +month;
            if(dueMonth>12 ) {
                int whole_year= (int) dueMonth/12; // 23/12 -> 1 11/12
                int partial_year=((dueMonth/12)-  whole_year) *12; // (1 11/12)-1 ->11/12 *12= 11

                year+=whole_year; //2023 +1
                month+=partial_year;
            }
//            LOGGER.info(String.valueOf(day));
//            LOGGER.info(String.valueOf(month));
//            LOGGER.info(String.valueOf(year));
//            LOGGER.info(String.valueOf(x.getMonthsToPay()));
            try{
                msg=String.format("%d-%d-%d",year, dueMonth, day );
                if( currentDate.after(simpleDateFormat.parse(msg))){
                    late_loans.add(x);
                }else{
                    ontime_loans.add(x);
                }

            }catch (Exception e){
                LOGGER.info("failed date parse: "+msg.toString());

            }
        }

//            LOGGER.info("here"+ontime_loans.size());
        model.addAttribute("user", user);
        model.addAttribute("money", money);
        model.addAttribute("monthlyInterest", Math.round(monthly_interest));

        model.addAttribute("ontime_loans",all_loans);
        model.addAttribute("late_loans",late_loans);
        return "UserPages/ShowUser";

    }

    @GetMapping("/edit")
    public String updateProfile(Model model){
        String username= SecurityUtil.getSessionUser();
        UserRegistrationDto dto= UserMapper.mapToDto(userService.findByUserName(username));
        model.addAttribute("user" ,dto);

//        model.addAttribute("profile",dto.)
        return "UserPages/EditUser";
    }

    @PostMapping("/edit")
    public String ss(
            Model model,
            @ModelAttribute("UserEntity")UserRegistrationDto user){
        model.addAttribute("x", user.getUserName());
        userService.updateUser(UserMapper.mapToModel(user));

        return "redirect:/profilez";
    }

//
//    @GetMapping("/about")
//    public String giveProfilePage(){
//        // this is for the general information about the user.
//
//    }


}
