package yurkov.cloudFileStorage.adapter.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yurkov.cloudFileStorage.adapter.web.dto.request.UserRegistrationRequest;
import yurkov.cloudFileStorage.domain.storage.user.UserEntity;
import yurkov.cloudFileStorage.service.RegistrationService;
import yurkov.cloudFileStorage.util.UserValidator;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/")
@Log4j2
public class AuthenticationController {

    RegistrationService registrationService;
    UserValidator userValidator;

    @GetMapping("login")
    public String getLoginForm() {
        return "auth/login";
    }

    @GetMapping("signup")
    public String getSignupForm(Model model) {
        model.addAttribute("user", new UserEntity());
        log.info("Get signup form");
        return "auth/signup";
    }

    @PostMapping("signup")
    public String registration(@ModelAttribute("user") @Validated UserRegistrationRequest user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            return "/auth/signup";
        }
        registrationService.registerUser(user);

        return "redirect:/auth/login";
        }
}
