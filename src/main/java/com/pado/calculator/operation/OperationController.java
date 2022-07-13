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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
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

        // 오퍼레이션 폼 변경 및 결과 연산
        Operation operation = operationService.operationCreate(operationForm);
        Object result = operationService.calculateExpression(operation);
        operation.setResult(result.toString());

        // 미로그인 유저일 경우 그냥 저장
        if(principal.equals("anonymousUser")){
            operationRepository.save(operation);
            return "/index";
        }

        // 로그인 유저일 경우 account에 저장
        Account account = accountRepository.findByEmail(principal.toString());
        operation.setAccount(account);
        operation.addAccount(account);

        model.addAttribute("result", result);

        operationRepository.save(operation);

        return "/index";
    }

    // todo: 잘못된 수식 잡아주는 exception. 나중에 전역 exception으로 넘기기
    @ExceptionHandler(ScriptException.class)
    public ModelAndView expressionExceptionCheck(ScriptException  e, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operationForm", new OperationForm());
        modelAndView.addObject("mathExpressionException", e);
        modelAndView.addObject("mathExpressionErrorMessage", "잘못된 수식이야");
        modelAndView.setViewName("index");

        return modelAndView;
    }

}
