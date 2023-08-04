package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.Payment;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.PaymentRepository;
import com.example.fairhandborrowing.repository.UserRepository;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.implement.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping
public class PaymentController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private CollateralService collateralService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentServiceImpl paymentService;

    @GetMapping("/payment/{userName}/{loanId}/schedule")
    public String paymentSchedule(
      @PathVariable("userName") String userName,
      @PathVariable("loanId") Long loanId,
      Model model) {
        Loan loan = loanService.getLoanById(loanId);
        List<Payment> payments = paymentRepository.findPaymentsByLoanId(loanId);
        model.addAttribute("loan", loan);
        model.addAttribute("payments", payments);
        model.addAttribute("paidCount", paymentService.getPaidCountStr());;

        UserEntity user = userRepository.findFirstByUsername(userName);
        model.addAttribute("user", user);
        return "loan/loan-payment";
    }

    @PostMapping("/payment/{userName}/{loanId}/{paymentId}/pay")
    public String payLoan(
            @PathVariable("userName") String userName,
            @PathVariable("loanId") Long loanId,
            @PathVariable("paymentId") Long paymentId,
            Model model) {
        paymentService.processPayment(paymentId);
        return "redirect:/payment/{userName}/{loanId}/schedule";
    }
}
