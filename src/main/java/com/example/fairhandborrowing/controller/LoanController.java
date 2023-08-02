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
