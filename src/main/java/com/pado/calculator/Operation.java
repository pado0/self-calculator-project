package com.pado.calculator;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Getter @Setter
public class Operation extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String operation;
}
