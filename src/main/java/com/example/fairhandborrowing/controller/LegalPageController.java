package com.example.fairhandborrowing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LegalPageController {

    @GetMapping(path="/legal/acceptable_use_policy")
    public String getAcceptableUsePolicy(){return "LegalPages/AcceptableUsePolicy";}

    @GetMapping(path="/legal/contact_us")
    public String getContactPage(){return "LegalPages/ContactUs";}

    @GetMapping(path="/legal/cookies_policy")
    public String getCookiesPolicy(){return "LegalPages/CookiesPolicy";}

    @GetMapping(path="/legal/disclaimer")
    public String getDisclaimer(){return "LegalPages/Disclaimer";}

    @GetMapping(path="/legal/insurance_policy")
    public String getInsurancePolicy(){return "LegalPages/InsurancePolicy";}

    @GetMapping(path="/legal/privacy_policy")
    public String getPrivacyPolicy(){return "LegalPages/PrivacyPolicy";}

}

