package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.dto.UserRegistrationDto;
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

import java.util.ArrayList;
import java.util.List;

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
        // TODO fetch collateral of borrower
        // TODO fetch loans of borrower
        List<Collateral> collaterals = collateralService.findAllCollaterals();
        List<Loan> loans = loanService.getAllNonArchivedLoansByUserId(user.getId());
        model.addAttribute("collaterals", collaterals);
        model.addAttribute("loans", loans);
        return "home/borrower";
      } else {
          List<LoanFunds> fundRequests = loanFundsService.getPendingRequestsForUser(userEntity);
          model.addAttribute("fundRequests", fundRequests);
          model.addAttribute("loans", new ArrayList<>());
          model.addAttribute("loanHistory", new ArrayList<>());
          return "home/lender";
      }
    }
        return "redirect:/login";
    }

}
