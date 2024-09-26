package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.security.SecurityUtil;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @PostMapping("/contract/{userName}/{loanId}/sign")
    public String signContract(@PathVariable("userName") String userName,
                                   @PathVariable("loanId") Long loanId) {

        contractService.signContract(userName, loanId);
        return "redirect:/home";
    }

}
