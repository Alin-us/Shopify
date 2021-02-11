package com.sda.miniemag.controller;

import com.sda.miniemag.model.ConfirmationToken;
import com.sda.miniemag.model.User;
import com.sda.miniemag.service.ConfirmationTokenService;
import com.sda.miniemag.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/sign-in")
    String signIn() {

        return "customer/login";
    }

    @GetMapping("/sign-up")
    ModelAndView signUp(@RequestParam(value = "error", required = false) Integer error) {
        ModelAndView modelAndView = new ModelAndView("customer/registration");
        modelAndView.addObject("user", new User());
        if (error != null && error == 1) modelAndView.addObject("error", "User already exists.");
        if (error != null && error == 2)
            modelAndView.addObject("error", "Minimum password length must be 6 characters.");
        return modelAndView;
    }

    @PostMapping("/sign-up")
    String signUp(User user) {

        int success = userService.signUpUser(user);
        switch (success) {
            case 0:
                return "redirect:/sign-in";
            default:
                return "redirect:/sign-up?error=" + success;
        }
    }


    @GetMapping("/sign-up/confirm")
    String confirmMail(@RequestParam("token") String token) {

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "redirect:/sign-in";
    }

}