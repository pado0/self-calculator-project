package com.pado.calculator.operation;

import com.pado.calculator.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Service
@RequiredArgsConstructor
public class OperationService {
    public Object calculateExpression(Operation operation) throws ScriptException {
        String expression = operation.getMathExpression();

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        Object result = engine.eval(expression);
        System.out.println("result = " + result);
        return result;
    }

    public Operation operationCreate(OperationForm operationForm) {
        Operation operation = Operation.builder()
                .mathExpression(operationForm.getMathExpression()).build();
        return operation;
    }

}
