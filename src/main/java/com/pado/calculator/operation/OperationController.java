package com.pado.calculator.operation;

import com.pado.calculator.account.Account;
import com.pado.calculator.account.SignUpForm;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("operationForm", new OperationForm());
        return "/index";
    }

    @PostMapping("/operation")
    public String operationPost(@Valid @ModelAttribute OperationForm operationForm, Model model, Errors errors) throws ScriptException {

        if(errors.hasErrors()){
            return "/index";
        }

        Operation operation = operationService.operationCreate(operationForm);
        Object result = operationService.calculateExpresstion(operation);
        model.addAttribute("result", result);
        return "/index";
    }
}
