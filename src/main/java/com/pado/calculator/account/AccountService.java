package com.pado.calculator.account;

import com.pado.calculator.operation.Operation;
import com.pado.calculator.operation.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final OperationRepository operationRepository;

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }

    @Transactional
    public Account createAccount(SignUpForm signUpForm) {
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        accountRepository.save(account);
        return account;
    }

    @Transactional
    public void saveUserCheckIfAnony(Operation operation, Object principal) {
        // 미로그인 유저일 경우 그냥 저장
        if(principal.equals("anonymousUser")) {
            operationRepository.save(operation);
        }else {

            // 로그인 유저일 경우 account에 저장
            Account account = accountRepository.findByEmail(principal.toString());
            operation.setAccount(account);
            //operation.addAccount(account);
            operationRepository.save(operation);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null){
            throw new UsernameNotFoundException(username);
        }
        return new UserAccount(account);
    }
}
