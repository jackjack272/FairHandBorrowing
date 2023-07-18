package com.example.fairhandborrowing.Controller;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.UserEntity;
import com.example.fairhandborrowing.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
public class RegistrationController {
//    https://www.baeldung.com/get-user-in-spring-security
//    https://stackoverflow.com/questions/61167576/securitycontextholder-import-not-working-in-spring-boot-application

    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //just ask chat gpt f google thats for the boomers.
    @GetMapping("/")
    public String landingPage(Principal principal, Model model){
        // Inside a method or class where you need the authenticated user

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
//            model.addAttribute("user",username);

        }
        model.addAttribute("user",principal.getName());


        return "LandingPage";
    }


    //todo:registration

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new UserRegistrationDto());
        return "Registration/Login";
    }


    @PostMapping("/login")
    public String logMeIN(Model model,
            @ModelAttribute("UserRegistrationDto") UserRegistrationDto dto ){

        model.addAttribute("email",dto.getEmail());
        model.addAttribute("password",dto.getPassword());
        // validate the password matches the email.

        if( userService.validateEmailWithPassword(dto.getEmail(), dto.getPassword())){
            return "worked";// send to landing page
        }else{
            return "redirect:/login";
        }

    }




}
