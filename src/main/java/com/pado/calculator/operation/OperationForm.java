package com.pado.calculator.operation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class OperationForm {

    @NotBlank
    @Length(min = 1, max = 100)
    private String mathExpression;

}
