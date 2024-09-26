package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.model.Transaction;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.security.SecurityUtil;
import com.example.fairhandborrowing.service.TransactionService;
import com.example.fairhandborrowing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/transactions")
    public String getTransactionsPage(Model model) {

        String username = SecurityUtil.getSessionUser();

        if(username != null) {
            UserEntity user = userService.findByUserName(username);
            List<Transaction> transactionList = transactionService.getTransactionsByUser(user);

            Collections.sort(transactionList,  (tx1, tx2) -> tx2.getCreationTimestamp().compareTo(tx1.getCreationTimestamp()));
            model.addAttribute("available_funds", user.getAvailableFunds());
            model.addAttribute("on_hold_funds", user.getFundsOnHold().toString());
            model.addAttribute("transactions", transactionList);

            return "transaction/transaction";
        }
        return "redirect:/";
    }

    @PostMapping("/transactions/funds/deposit")
    public String addFunds(@RequestParam(value = "depositAmount", required = true) Double depositAmount) {
        String username = SecurityUtil.getSessionUser();

        if(username != null) {
            UserEntity user = userService.findByUserName(username);
            transactionService.depositMoney(BigDecimal.valueOf(depositAmount), user);
            return "redirect:/transactions";
        }
        return "redirect:/";
    }

    @PostMapping("/transactions/funds/withdraw")
    public String withdrawFunds(@RequestParam(value = "withdrawAmount", required = true) Double withdrawAmount) {
        String username = SecurityUtil.getSessionUser();

        if(username != null) {
            UserEntity user = userService.findByUserName(username);
            transactionService.withdrawMoney(BigDecimal.valueOf(withdrawAmount), user);
            return "redirect:/transactions";
        }

        return "redirect:/";
    }

}
