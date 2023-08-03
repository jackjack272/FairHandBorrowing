package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.dto.LoanFundDto;
import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.LoanFundsService;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.service.UserService;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class HomeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CollateralService collateralService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanFundsService loanFundsService;

    @GetMapping("/")
    public String getIndex() {
        String username = SecurityUtil.getSessionUser();

        if(username == null) {
            return "redirect:/login";
        }

        return "redirect:/home";
  }

    @GetMapping("/home")
    public String getHome(Model model) {
        UserRegistrationDto user;
        String username = SecurityUtil.getSessionUser();

        LOGGER.info("user" + username);

    if (username != null) {
      UserEntity userEntity = userService.findByUserName(username);
      user = UserMapper.mapToDto(userEntity);
      model.addAttribute("user", user);
      if (user.getProfileType().equalsIgnoreCase("BORROWER")) {
        model.addAttribute("userType", "borrower");
        List<Collateral> collaterals = collateralService.findAllCollaterals();
        List<Loan> loans = loanService.getAllNonArchivedLoansByUserId(user.getId());
        List<LoanDto> loanDtos = new ArrayList<>();
        loanService.prepareDtos(loanDtos, loans, userEntity);

        List<Loan> historyLoans = loanService.getAllCompletedAndArchivedLoansByUserId(user.getId());
        List<LoanDto> loanHistoryDtos = new ArrayList<>();
        loanService.prepareDtos(loanHistoryDtos, historyLoans, userEntity);


        model.addAttribute("collaterals", collaterals);
        model.addAttribute("loans", loanDtos);
        model.addAttribute("loans_history", loanHistoryDtos);
        return "home/borrower";
      } else {
          List<LoanFunds> fundRequests = loanFundsService.getPendingRequestsForUser(userEntity);

          List<LoanFundDto> loanFundDtos = new ArrayList<>();

          fundRequests.stream().forEach(fundRequest -> {
              Loan loan = loanService.getLoanById(fundRequest.getLoan().getId());
              Double totalFunded = loanFundsService.calculateTotalFunded(fundRequest.getLoan().getId());

              LoanFundDto.LoanFundDtoBuilder dto = LoanFundDto.builder();
              dto.loanFund(fundRequest);
              dto.remainingFundsRequired(loan.getAmountBorrowed() - totalFunded);

              loanFundDtos.add(dto.build());
          });

          List<Loan> acceptedLoans = loanFundsService.getAcceptedRequestsForUser(userEntity).stream().map(loanFund -> loanFund.getLoan()).collect(Collectors.toList());
          List<LoanDto> loanDtos = new ArrayList<>();
          loanService.prepareDtos(loanDtos, acceptedLoans, userEntity);
          model.addAttribute("available_funds", userEntity.getAvailableFunds().doubleValue());
          model.addAttribute("fundRequests", loanFundDtos);
          model.addAttribute("loans", loanDtos);
          return "home/lender";
      }
    }
        return "redirect:/login";
    }

}
