package com.pado.calculator.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    Account findByEmail(String email);


}
