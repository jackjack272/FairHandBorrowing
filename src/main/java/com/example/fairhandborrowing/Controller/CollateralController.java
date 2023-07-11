package com.example.fairhandborrowing.Controller;


import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Service.CollateralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class CollateralController {

    private CollateralService service;

    @Autowired
    public CollateralController(CollateralService service) {
        this.service = service;
    }



    @GetMapping("/collateral")
    public String showCollateral(Model model){
        model.addAttribute("all", service.findAllCollateral());
        model.addAttribute("collateral",new CollateralDto());

        return "Loan/Collateral";
    }

    @PostMapping("/collateral/add")
    public String addCollateral(@Valid @ModelAttribute("CollateralDto") CollateralDto collateralDto,
                                BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "redirect:/collateral";
//        }

        service.addNewCollateral(collateralDto);

        return "redirect:/collateral";
    }


}
