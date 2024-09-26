package com.example.fairhandborrowing.controller;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.model.Collateral;
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

        String erro=validateInputs(collateralDto);
        if(!erro.equals("")){
            model.addAttribute("fail",erro);// i realise this wont be able to send the message
            return "collateral/collateral-create";
        }

        collateralService.createCollateral(userName, collateralDto);
        return "redirect:/home";
    }

    private Boolean ensureNoSpecialChars(String theDescription){
        for(char x:theDescription.toCharArray()){
            if ( (int) x == 32) {
                continue;
            }
            if(! String.valueOf(x).matches( "[A-Za-z0-9,.?!';-]") ){
                return true;
            }
        }
        return false;
    }

    private String validateInputs(CollateralDto collateralDto){
        if (collateralDto.getBrand()==null){
            return "brand name cant be null";
        }
        if(ensureNoSpecialChars(collateralDto.getBrand())){
            return "brand cant have those special characters";
        }
        if (collateralDto.getItemName()==null){
            return "enter the item name ";
        }
        if (collateralDto.getDescription()==null){
            return "enter the description";
        }
        if (collateralDto.getMarketValue()==null){
            return "enter the market value";
        }
        return "";
    }


    @PostMapping("/collateral/{userName}/{collateralId}/delete")
    public String deleteCollateral(@PathVariable("userName") String userName,
                                   @PathVariable("collateralId") Long collateralId) {

        collateralService.deleteCollateral(collateralId);
        return "redirect:/home";
    }

}
