package com.pado.calculator.operation;

import com.pado.calculator.account.Account;
import com.pado.calculator.account.AccountRepository;
import com.pado.calculator.account.AccountService;
import com.pado.calculator.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
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

import static org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.*;

@Controller
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @GetMapping("/")
    public String index(Model model
            , @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("operationForm", new OperationForm());

        if (userAccount != null) {
            model.addAttribute("userAccount", userAccount.getAccount().getId());
            System.out.println("userAccount.getAccount().getId() = " + userAccount.getAccount().getId());
        }
        return "index";
    }


    @PostMapping("/operation")
    public String operationPost(@Valid @ModelAttribute OperationForm operationForm,
                                Model model,
                                Errors errors,
                                @AuthenticationPrincipal UserAccount userAccount) throws ScriptException {

        if (errors.hasErrors()) {
            return "index";
        }

        // ??????????????? ??? ?????? ??? ?????? ??????
        Operation operation = operationService.operationCreate(operationForm);
        Object result = operationService.calculateExpression(operation);
        operation.setResult(result.toString());

        // account ????????? ????????? ?????? ??? ??????, anony??? null??? ??????
        if (userAccount != null) {
            accountService.saveUserCheck(operation, userAccount.getUsername());
            model.addAttribute("userAccount", userAccount.getAccount().getId());
        }
        else accountService.saveWithoutUser(operation);

        model.addAttribute("result", result);
        return "index"; // todo : ???????????? ?????? ????????? ?????? redirect ?????? addAttribute result??? ??????
    }

    @GetMapping("/operation/history/{accountId}")
    public String getHistoryByJoinedAccount(@PathVariable("accountId") Long accountId,
                                            Model model,
                                            @AuthenticationPrincipal UserAccount userAccount) {

        // ?????? ???????????? ????????? id??? pathvariable??? ????????? ???????????? ???????????? ??????.
        if (userAccount != null && userAccount.getAccount().getId() != accountId) {
            model.addAttribute("operationForm", new OperationForm());
            /// todo : error ???????????? ??????
            return "error";
        }

        List<Operation> operations = operationRepository.findByAccountId(accountId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("operationForm", new OperationForm());
        model.addAttribute("operations", operations);
        model.addAttribute("userAccount", userAccount.getAccount().getId());

        return "index";
    }

    @GetMapping("/operation/history")
    public String getHistoryForAnonymousAccount(Model model) {
        List<Operation> operations = operationRepository.findByAccountId(null);

        model.addAttribute("operationForm", new OperationForm());
        model.addAttribute("operations", operations);

        return "index";
    }


    // todo: ????????? ?????? ???????????? exception. ????????? ?????? exception?????? ?????????
    @ExceptionHandler(ScriptException.class)
    public ModelAndView expressionExceptionCheck(ScriptException e, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operationForm", new OperationForm());
        modelAndView.addObject("mathExpressionException", e);
        modelAndView.addObject("mathExpressionErrorMessage", "????????? ????????????");
        modelAndView.setViewName("index");

        return modelAndView;
    }

}
