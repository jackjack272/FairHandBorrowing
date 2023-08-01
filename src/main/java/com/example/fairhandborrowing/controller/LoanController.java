package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.service.*;
import com.example.fairhandborrowing.model.ProfileType;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping
public class LoanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    private static final String LENDER_VALUE = "LENDER";

    @Autowired
    private LoanService loanService;

    @Autowired
    private CollateralService collateralService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileTypeService profileTypeService;

    @Autowired
    private LoanFundsService loanFundsService;

    @GetMapping("/loan/{userName}/new" )
    public String craeteLoanForm(@PathVariable("userName") String userName, Model model) {
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
        if(result.hasErrors()) {
            model.addAttribute("loan", loanDto);
            return "loan/loan-create";
        }

        loanService.createLoan(userName, loanDto);

        return "redirect:/home";
    }

    @GetMapping("/loan/{userName}/edit")
    public String editLoan(
      @PathVariable("userName") String userName,
      @RequestParam("loan_id") Long loanId,
      Model model) {

        Loan loan = loanService.getLoanById(loanId);
        LoanDto loanDto = LoanMapper.mapToDto(loan);
        List<Collateral> collaterals = collateralService.findAllCollateralsByUsername(userName);
        model.addAttribute("allcollaterals", collaterals);
        model.addAttribute("loan", loanDto);
        return "loan/loan-edit";
    }

    @GetMapping("/requestFunds/{userName}/{loanId}/new")
    public String createReqFundsPage(@PathVariable("userName") String userName, @PathVariable("loanId") Long loanId, Model model) {
        String loggedUsername = SecurityUtil.getSessionUser();

        ProfileType profileType = profileTypeService.getProfileByType(LENDER_VALUE);
        Loan loan = loanService.getLoanById(loanId);
        List<UserEntity> lenders = userService.findAllByType(profileType);
        model.addAttribute("userName", userName);
        model.addAttribute("loan", loan);
        model.addAttribute("lenders", lenders);

        return "loan/loan-fund";
    }

    @PostMapping("/requestFunds/{username}/{loanId}/new")
    public String requestFunds(@PathVariable("username") String username,
                               @PathVariable("loanId") Long loanId,
                               @RequestParam(value = "lenderUsernames", required = false) String[] lenderUsernames,
                               Model model) {

        String loggedUsername = SecurityUtil.getSessionUser();

        if(loggedUsername.equalsIgnoreCase(username)) {

            for(String lenderUsername : lenderUsernames) {
                UserEntity borrower = userService.findByUserName(username);
                UserEntity lender = userService.findByUserName(lenderUsername);
                Loan loan = loanService.getLoanById(loanId);
                loanFundsService.sendFundRequest(borrower, lender, loan);
            }
    }
        return "redirect:/home";
    }

}
