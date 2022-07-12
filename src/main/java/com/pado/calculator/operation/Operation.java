package com.pado.calculator.operation;

import com.pado.calculator.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Getter @Setter
@Builder
public class Operation extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String mathExpression;

    private String result;
}
