package com.pado.calculator.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

//    @Test
//    void 회원_가입_성공() throws Exception {
//        mockMvc.perform(post("/sign-up")
//                        .param("email", "gywls17431@naver.com")
//                        .param("password", "asdfqwer")
//                        .with(csrf()))
//                .andExpect(authenticated().withUsername("gywls17431@naver.com")); // UsernamePasswordAuthToken 구현 후 동작.
//
//        Account account = accountRepository.findByEmail("gywls17431@naver.com");
//        assertNotNull(account);
//        assertNotEquals(account.getPassword(), "asdfqwer");
//
//    }
    @Test
    void 회원_가입_입력값오류() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("email", "gywls174xxx")
                        .param("password", "asdfq")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up"))
                .andExpect(unauthenticated()); // UsernamePasswordAuthToken 구현 후 동작.

    }

}