package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.dto.ContractDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.service.*;
import com.example.fairhandborrowing.security.SecurityUtil;
import com.example.fairhandborrowing.repository.LoanRepository;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private CollateralService collateralService;

    @GetMapping("/loan/{userName}/new" )
    public String craeteLoanForm(@PathVariable("userName") String userName, Model model) {
//        LoanDto loan = new LoanDto( (long)3,-3,-12,-4,"SELECT * FROM Users WHERE UserId = ", false,true, new LoanStatus(), new Date(), new UserEntity(), "2",4,new ContractDto());
        LoanDto loan = new LoanDto();

        List<Collateral> collaterals = collateralService.findAllCollateralsByUsername(userName);
        model.addAttribute("userName", userName);
        model.addAttribute("loan", loan);
        model.addAttribute("collaterals", collaterals);
        return "loan/loan-create";
    }

    @PostMapping("/loan/{userName}/new")
    public String addLoan(@PathVariable("userName") String userName, @ModelAttribute("loan") LoanDto loanDto,
                                   BindingResult result,
                                   Model model) {

        String theError=validateInput(loanDto);
        if (!theError.equals("")){
            model.addAttribute("fail",theError);
            return "loan/loan-create";
        };


        if(result.hasErrors()) {
            model.addAttribute("loan", loanDto);
            return "loan/loan-create";
        }

        loanService.createLoan(userName, loanDto);
        return "redirect:/home";
    }

    private Boolean ensurePositiveNums(double num){
        if(num <=0 || num>2850){
            return true;
        }
        return false;
    }
    private Boolean ensureNoSpecialChars(String theDescription){
        for(char x:theDescription.toCharArray()){
            if ( (int) x == 32) {
                continue;
            }
            if(! String.valueOf(x).matches( "[A-Za-z0-9,.?!';-]") ){
                return true;
            }
        }
        return false;
    }
    private String validateInput(LoanDto loanDto){
        if(loanDto.getAmountBorrowed()==null ||loanDto.getInterestRate()== null||
                loanDto.getDescription()==null || loanDto.getMonthsToPay()==null ){
            return "fill in all the fields ";
        }

        // need to ensure total amount, intereat rate, terms are positive numbers.
        //loan cant be null
        // description cant have symbols
        if(ensurePositiveNums(loanDto.getAmountBorrowed())){
            return ("the amount borrowed cant be negative or over 2850");
        }
        if(ensurePositiveNums(loanDto.getInterestRate())) {
            return ("interest rate cant be negative");
        }
        if(ensurePositiveNums(loanDto.getMonthsToPay())) {
            return ("months of loan cant be negative");
        }

        if(ensureNoSpecialChars(loanDto.getDescription())){
            return ("The special characters you've user are not allowed; you can use 0-9 ? ! , . ' '");
        }
        return "";
    }


    @GetMapping("/loan/{userName}/{loanId}/edit")
    public String editLoan(
      @PathVariable("userName") String userName,
      @PathVariable("loanId") Long loanId,
      Model model) {

        Loan loan = loanService.getLoanById(loanId);
        LoanDto loanDto = LoanMapper.mapToDto(loan);
        List<Collateral> collaterals = collateralService.findAllCollateralsByUsername(userName);
        model.addAttribute("userName", userName);
        model.addAttribute("allcollaterals", collaterals);
        model.addAttribute("loan", loanDto);
        return "loan/loan-edit";
    }

    @PostMapping("/loan/{userName}/edit")
    public String editLoan(@PathVariable("userName") String userName,
                           @RequestParam("loanId") Long loanId,
                           @ModelAttribute("loan") LoanDto loanDto,
                           @RequestParam(value = "collaterals", required = false) String[] collaterals,
                           Model model) {
        loanService.editLoan(loanId, loanDto, collaterals);
        return "redirect:/home";
    }

    @PostMapping("/loan/{userName}/archive")
    public String archiveLoan(@PathVariable("userName") String userName,
                              @RequestParam("loanId") Long loanId) {

        loanService.archiveLoan(loanId);
        return "redirect:/home";
    }
}
