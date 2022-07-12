package com.pado.calculator.operation;

import com.pado.calculator.account.Account;
import com.pado.calculator.account.AccountRepository;
import com.pado.calculator.account.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("operationForm", new OperationForm());
        return "/index";
    }

    @PostMapping("/operation")
    public String operationPost(@Valid @ModelAttribute OperationForm operationForm,
                                Model model,
                                Errors errors) throws ScriptException {

        if(errors.hasErrors()){
            return "/index";
        }

        // 회원 email context holder에서 읽어오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // todo : AnonyUser 분기처리
        Account account = accountRepository.findByEmail(principal.toString());

        Operation operation = operationService.operationCreate(operationForm);
        Object result = operationService.calculateExpression(operation);

        operation.setResult(result.toString());
        operation.setAccount(account);
        operation.addAccount(account);

        model.addAttribute("result", result);

        operationRepository.save(operation);

        return "/index";
    }
}
