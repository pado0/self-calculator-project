package com.pado.calculator.operation;

import com.pado.calculator.account.Account;
import com.pado.calculator.account.AccountRepository;
import com.pado.calculator.account.AccountService;
import com.pado.calculator.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("operationForm", new OperationForm());
        return "/index";
    }


    @PostMapping("/operation")
    public String operationPost(@Valid @ModelAttribute OperationForm operationForm,
                                Model model,
                                Errors errors,
                                @AuthenticationPrincipal UserAccount userAccount) throws ScriptException {

        if(errors.hasErrors()){
            return "/index";
        }

        // 오퍼레이션 폼 변경 및 결과 연산
        Operation operation = operationService.operationCreate(operationForm);
        Object result = operationService.calculateExpression(operation);
        operation.setResult(result.toString());

        // 회원 email context holder에서 읽어오기
        // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // account 정보가 있으면 회원 별 저장, anony면 null로 저장
        accountService.saveUserCheckIfAnony(operation, userAccount.getUsername());

        model.addAttribute("result", result);
        return "/index"; // todo : 새로고침 이슈 해결을 위해 redirect 하면 addAttribute result가 안됨
    }

    @GetMapping("/operation/history/{accountId}")
    public String getHistoryByJoinedAccount(@PathVariable("accountId") Long accountId, Model model){
        List<Operation> operations = operationRepository.findByAccountId(accountId);
        System.out.println("operations = " + operations.get(0).getMathExpression());
        model.addAttribute("operationForm", new OperationForm());
        model.addAttribute("operations" , operations);

        return "/index";
    }

    @GetMapping("/operation/history")
    public String getHistoryForAnonymousAccount(Model model){
        List<Operation> operations = operationRepository.findByAccountId(null);
        model.addAttribute("operationForm", new OperationForm());
        model.addAttribute("operations" , operations);

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
