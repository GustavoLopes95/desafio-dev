package com.bycoders.challangebycoders.useCases;

import com.bycoders.challangebycoders.TestBase;
import com.bycoders.challangebycoders.UseCasesConfig;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = UseCasesConfig.class)
public class ImportStatementsListUseCaseTest extends TestBase {

    @MockBean
    private StatementRepository repository;

    @Autowired
    private ImportStatementsListUseCase importStatementsListUseCase;

    @Test
    @DisplayName("Service - should import a statement")
    public void importStatement() throws Exception {
        var command = this.makeImportStatementListCommand();
        var statement = this.makeStatement();
        BDDMockito.given(repository.save(statement)).willReturn(statement);

        var response = importStatementsListUseCase.execute(command);

        Assertions.assertThat(response.get("message")).isEqualTo("Todos as transações foram importadas com sucesso!");

    }
}
