package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.LoanService;
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

    @Autowired
    private LoanService loanService;

    @Autowired
    private CollateralService collateralService;

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
    public String createCollateral(@PathVariable("userName") String userName, @ModelAttribute("loan") LoanDto loanDto,
                                   BindingResult result,
                                   Model model) {
        if(result.hasErrors()) {
            model.addAttribute("loan", loanDto);
            return "loan/loan-create";
        }

        loanService.createLoan(userName, loanDto);

        return "redirect:/home";
    }

}
