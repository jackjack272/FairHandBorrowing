package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.ProfileType;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.LoanFundsRepository;
import com.example.fairhandborrowing.security.SecurityUtil;
import com.example.fairhandborrowing.service.LoanFundsService;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.ProfileTypeService;
import com.example.fairhandborrowing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class FundsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);
    private static final String LENDER_VALUE = "LENDER";

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileTypeService profileTypeService;

    @Autowired
    private LoanFundsService loanFundsService;
    @Autowired
    private LoanFundsRepository loanFundsRepository;

    @GetMapping("/funds/{userName}/{loanId}/request")
    public String createReqFundsPage(@PathVariable("userName") String userName, @PathVariable("loanId") Long loanId, Model model) {
        String loggedUsername = SecurityUtil.getSessionUser();

        ProfileType profileType = profileTypeService.getProfileByType(LENDER_VALUE);
        Loan loan = loanService.getLoanById(loanId);
        List<UserEntity> lenders = userService.findAllByType(profileType);

        List<UserEntity> existingLoanLenders = loanFundsRepository.findByLoanId(loanId).stream().map(lf -> {
            return lf.getLender();
        }).collect(Collectors.toList());


        lenders = lenders.stream().filter(lender ->!existingLoanLenders.contains(lender)).collect(Collectors.toList());

        model.addAttribute("userName", userName);
        model.addAttribute("loan", loan);
        model.addAttribute("lenders", lenders);

        return "loan/loan-fund";
    }

    @PostMapping("/funds/{userName}/{loanId}/request")
    public String requestFunds(@PathVariable("userName") String username,
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

    @PostMapping("/funds/{userName}/{fundLoanId}/acceptance")
    public String acceptFunds(@PathVariable("userName") String username,
                               @PathVariable("fundLoanId") Long fundLoanId,
                               @RequestParam(value = "fundAmount", required = true) Double fundAmount){
        loanFundsService.acceptFundRequest(fundLoanId, fundAmount);
        return "redirect:/home";
    }

    @PostMapping("/funds/{userName}/{fundLoanId}/rejection")
    public String rejectFunds(@PathVariable("userName") String username,
                              @PathVariable("fundLoanId") Long fundLoanId) {
        String loggedUsername = SecurityUtil.getSessionUser();

        if(loggedUsername.equalsIgnoreCase(username)) {
            loanFundsService.rejectFundRequest(fundLoanId);
        }

        return "redirect:/home";
    }

}
