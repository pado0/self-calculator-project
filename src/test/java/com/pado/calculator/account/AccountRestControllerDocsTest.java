package com.pado.calculator.account;

import com.pado.calculator.api.AccountRestController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(RestDocumentationExtension.class)    // JUnit5 필수
@WebMvcTest(AccountRestController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountRestControllerDocsTest {
}
