package com.example.fairhandborrowing.Controller;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Service.ProfileTypeService;
import com.example.fairhandborrowing.Service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class AuthenticationController {
    //    https://www.baeldung.com/get-user-in-spring-security
//    https://stackoverflow.com/questions/61167576/securitycontextholder-import-not-working-in-spring-boot-application

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileTypeService profileTypeService;

    //todo:edit profile
    //create the account generation form
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("user_obj",userService.findByUserName(username));
            // good /bad standing
        }
        return "Registration/Profile";
    }

    @GetMapping("/profileform")
    public String makeProfile(){


        return "Registration/Profile";

    }


    //todo:registration
    @GetMapping("/register")
    public String getRegistrationPage(Model model){
        model.addAttribute("user",new UserRegistrationDto());
        return "Registration/Register";
    }

    @ModelAttribute("profileTypes")
    public List<String> getProfileTypes() {
        return profileTypeService.getProfileTypes();
    }

    @PostMapping("/register")
    public String saveUser(Model model,
                           @ModelAttribute("UserRegistrationDto")UserRegistrationDto dto,
                           BindingResult result) {

            LOGGER.info("Registration DTO: " + dto);

            if (userService.findByUserName(dto.getUserName()) != null) {
                model.addAttribute("failed","username already taken.");
                return "redirect:/register?fail";
            } else if (userService.findByEmail(dto.getUserName()) != null) {
                model.addAttribute("failed", "email already taken.");
                return "redirect:/register?fail";
            }

            if(result.hasErrors()) {
                model.addAttribute("user", dto);
                return "Registration/Register";
            }

            Optional<UserRegistrationDto> user = userService.saveUser(dto);

            if(user.isPresent()) {
                LOGGER.info("Registration success: " + user.get().getUserName());
            }

            return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new UserRegistrationDto());
        return "Registration/Login";
    }

}
