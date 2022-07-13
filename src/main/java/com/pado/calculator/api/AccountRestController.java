package com.pado.calculator.api;

import com.pado.calculator.account.Account;
import com.pado.calculator.account.AccountService;
import com.pado.calculator.account.SignUpForm;
import com.pado.calculator.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;

    @GetMapping("/api/test")
    public ResponseEntity test(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/sign-up")
    public Result signUp(@Valid @RequestBody SignUpForm signUpForm){

        Account account = accountService.createAccount(signUpForm);
        return new Result<>(account, "success");
    }
}
