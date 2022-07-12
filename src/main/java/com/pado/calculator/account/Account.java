package com.pado.calculator.account;

import com.pado.calculator.common.BaseEntity;
import com.pado.calculator.operation.Operation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "account")
    private List<Operation> operations = new ArrayList<>();
}
