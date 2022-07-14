package com.pado.calculator.api;

import com.pado.calculator.account.Account;
import com.pado.calculator.account.AccountRepository;
import com.pado.calculator.account.AccountService;
import com.pado.calculator.account.SignUpForm;
import com.pado.calculator.common.Result;
import com.pado.calculator.exception.AccountCannotCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

//    @ExceptionHandler
//    public ResponseEntity handleException(AccountCannotCreateException e){
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }


    @GetMapping("/api/test")
    public ResponseEntity test(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/sign-up")
    public Result<Account> signUp(@Valid @RequestBody SignUpForm signUpForm){


        // todo : 비즈니스 로직에 의한 예외사항 발생, 서비스 레이어로 내리기. Exception은 글로벌에서 처리
        if(accountRepository.existsByEmail(signUpForm.getEmail()))
            throw new AccountCannotCreateException("이미 존재하는 회원입니다");

        Account account = accountService.createAccount(signUpForm);

        // todo : Result 부분 생성자로 리팩토링. 지네릭 다시공부하기
        // urclass exception errorresult 부분 참고하기
        Result<Account> result = Result.<Account>builder()
                .status(HttpStatus.OK)
                .message("정상응답")
                .data(account).build();

        return result;
    }
}
