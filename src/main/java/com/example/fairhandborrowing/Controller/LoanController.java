package com.example.fairhandborrowing.Controller;

import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Service.CollateralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoanController {
    private CollateralService collateralService;

    @Autowired
    public LoanController(CollateralService collateralService) {
        this.collateralService = collateralService;
    }

    @GetMapping("/loan")
    public String getLoanPage(Model model){
        model.addAttribute("collateral", new CollateralDto() );
        model.addAttribute("collateralList",collateralService.findAllCollateral());

        return "Loan/Loan";
    }

}
