package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@Controller
public class CollateralController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollateralController.class);

    @Autowired
    private CollateralService collateralService;

    @GetMapping("/collateral/{userName}/new")
    public String createCollateralForm(@PathVariable("userName") String username, Model model) {
        CollateralDto collateral = new CollateralDto();

        String loggedUsername = SecurityUtil.getSessionUser();

        LOGGER.info(loggedUsername + "is logged in");

        model.addAttribute("userName", username);
        model.addAttribute("collateral", collateral);
        return "collateral/collateral-create";
    }

    @PostMapping("/collateral/{userName}/new")
    public String createCollateral(@PathVariable("userName") String userName, @ModelAttribute("collateral") CollateralDto collateralDto,
                                   BindingResult result,
                                   Model model) {
        if(result.hasErrors()) {
            model.addAttribute("collateral", collateralDto);
            return "collateral/collateral-create";
        }

        collateralService.createCollateral(userName, collateralDto);
        return "redirect:/home";
    }

    @PostMapping("/collateral/{userName}/{collateralId}/delete")
    public String deleteCollateral(@PathVariable("userName") String userName,
                                   @PathVariable("collateralId") Long collateralId) {

        collateralService.deleteCollateral(collateralId);
        return "redirect:/home";
    }

}
