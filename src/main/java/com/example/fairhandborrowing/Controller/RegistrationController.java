package com.example.fairhandborrowing.Controller;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

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
            model.addAttribute("user",username);
        }
        ;
//        model.addAttribute("user",
//                userService.findByUserName(principal.getName()).getFirstName() );
        // these are both the ways to get the user.
            //Authentication and Principal

        return "LandingPage";
    }

    //create the account generation form
    @GetMapping("/profile")
    public String profileSetup(Model model, Principal principal){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("user_obj",userService.findByUserName(username));
            // good /bad standing
        }

        return "Registration/Profile";
    }


    //todo:registration
    @GetMapping("/register")
    public String getRegistrationPage(Model model){
        model.addAttribute("user",new UserRegistrationDto());
        return "Registration/RegisterPage";
    }

    @PostMapping("/register")
    public String saveUser(Model model,
            @ModelAttribute("UserRegistrationDto")UserRegistrationDto dto ) {
        try{
            userService.saveUser(dto);
            return "redirect:/";
        }catch (Exception e){
            model.addAttribute("user",dto);
            model.addAttribute("failed",e.getMessage());
            model.addAttribute("here","failed to send ");
            return "Registration/RegisterPage";
//            return "worked";

        }

//        return "worked";
    };





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
