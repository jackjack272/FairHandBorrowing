package com.example.fairhandborrowing.Controller;

import com.example.fairhandborrowing.DTO.LoanDto;
import com.example.fairhandborrowing.DTO.MoveMoneyDto;
import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.MoveMoney;
import com.example.fairhandborrowing.Model.UserEntity;
import com.example.fairhandborrowing.Service.LoanService;
import com.example.fairhandborrowing.Service.MoveMoneyService;
import com.example.fairhandborrowing.Service.UserService;
import com.example.fairhandborrowing.Service.implement.MoveMoneyServiceImpl;
import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class MoneyController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    Double Amount;
    UserService userService;
    MoveMoneyService moneyService;
    LoanService loanService;

    public MoneyController(UserService userService,LoanService loanService, MoveMoneyService moveMoneyService ) {
        this.userService = userService;
        this.loanService= loanService;
        this.moneyService = moveMoneyService;
    }


    @GetMapping("/money")
    public String getMoney(Model model){
        // make this link be able to handle deposit as well.

        String usernmae= SecurityUtil.getSessionUser();
        UserRegistrationDto userRegistrationDto= userService.findByUserName(usernmae) ;

        Amount=500.9;
        model.addAttribute("amount",Amount);
        model.addAttribute("moveMoney", new MoveMoney());

        return "Money/withdraw_deposit";
    }


    // withdraw
    @PostMapping("/withdraw")
    public String withdrawMoney(
            @ModelAttribute("MoveMoney") MoveMoneyDto money){
        if( money.getAmount()==null ||
                money.getDestination() ==null||
                money.getAmount() >Amount
            ){
            return "redirect:/money";
        }
        money.setWithdrawn(true);

        LOGGER.info("about to save");
        moneyService.save(money);
        LOGGER.info("saved");

        return "redirect:/edit";
    }

    // deposit
//    @PostMapping("/deposit")





}




//----- old code might be handy
//save the  withdraw
////        moneyService.save(money);
//        LOGGER.info("saved the object"+ money.getAmount());
//        LOGGER.info("saved the object"+ money.getDestination());
//        LOGGER.info("saved the object"+ money.getUser());
////        LOGGER.info("saved the object"+ money.getId()); this is null since not in the db
//        LOGGER.info("saved the object"+ money.getWithdrawn());
//
