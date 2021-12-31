package com.bycoders.challangebycoders.controller;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.exceptions.DomainException;
import com.bycoders.challangebycoders.useCase.ImportStatementsList;
import com.bycoders.protobuf.StatementsProtos;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class StatementControllerTest {

    static String STATEMENT_API = "/api/v1/importStatements";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    ImportStatementsList importStatementsUseCase;

    @Test
    @DisplayName("API - receive statement list")
    public void importStatementListTest() throws Exception {
        var statementsList = StatementsProtos.Statements.newBuilder()
                .addStatement("3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO")
                .addStatement("5201903010000013200556418150633123****7687145607MARIA JOSEFINALOJA DO Ó - MATRIZ")
                .build();

        var payload = statementsList.toByteArray();
        var request = MockMvcRequestBuilders.post(STATEMENT_API)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload);

        var successMessage = "Todos as transações foram importadas com sucesso";
        BDDMockito.given(importStatementsUseCase.execute(Mockito.any(ImportStatementListCommand.class)))
                .willReturn(Map.of("message", successMessage));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(successMessage));
    }

    @Test
    @DisplayName("API - shoudl throw domain exception when fail process statements")
    public void importInvalidStatementListTest() throws Exception {
        var statementsList = StatementsProtos.Statements.newBuilder()
                .build();

        var payload = statementsList.toByteArray();
        var request = MockMvcRequestBuilders.post(STATEMENT_API)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload);

        var message = "A lista não pode estar vazia";
        BDDMockito.given(importStatementsUseCase.execute(Mockito.any(ImportStatementListCommand.class)))
                .willThrow(new DomainException(message, "Statement"));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(message));
    }


}
