package com.pado.calculator.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/sign-up")
    public String signUpGet(Model model){

        model.addAttribute("signUpForm", new SignUpForm());

        return "/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpPost(@Valid @ModelAttribute SignUpForm signUpForm) {

        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        accountRepository.save(account);

        return "redirect:/";
    }

}
