package com.pado.calculator.account;

import com.pado.calculator.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
}
