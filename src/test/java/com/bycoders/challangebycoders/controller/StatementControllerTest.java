package com.bycoders.challangebycoders.controller;

import com.bycoders.challangebycoders.useCase.ImportStatementsList;
import com.bycoders.protobuf.StatementsProtos;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class StatementControllerTest {

    static String STATEMENT_API = "/api/v1/statement";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    ImportStatementsList importStatementsUseCase;

    @BeforeAll
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("API - receive statement list")
    public void importStatementListTest() throws Exception {
        var statementsList = StatementsProtos.Statements.newBuilder()
                .addStatement("3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO")
                .addStatement("5201903010000013200556418150633123****7687145607MARIA JOSEFINALOJA DO Ó - MATRIZ")
                .build();

        var request = MockMvcRequestBuilders.post(STATEMENT_API)
                .contentType("application/x-protobuf")
                .accept(MediaType.APPLICATION_JSON)
                .content(payload.toByteArray());
        BDDMockito.given(importStatementsUseCase.execute(statementsList)).willReturn(true);
        var payload = statementsList.toByteString();

        mvc.perform(request).().andExpect(MockMvcResultMatchers.status().isOk());
    }


}
