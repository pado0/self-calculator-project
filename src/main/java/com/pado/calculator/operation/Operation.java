package com.pado.calculator.operation;

import com.pado.calculator.account.Account;
import com.pado.calculator.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Operation extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String mathExpression;

    private String result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // 연관관계 편의 메소드. operation 추가시 account에도 operation 추가해줌.
    public void addAccount(Account account) {
        account.getOperations().add(this);
    }

}
