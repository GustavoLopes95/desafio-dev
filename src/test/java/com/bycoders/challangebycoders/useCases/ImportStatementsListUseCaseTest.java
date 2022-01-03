package com.bycoders.challangebycoders.useCases;

import com.bycoders.challangebycoders.TestBase;
import com.bycoders.challangebycoders.UseCasesConfig;
import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.repositories.ClientRepository;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = UseCasesConfig.class)
public class ImportStatementsListUseCaseTest extends TestBase {

    @MockBean
    StatementRepository statementRepository;

    @MockBean
    ClientRepository clientRepository;

    @Autowired
    private ImportStatementsListUseCase importStatementsListUseCase;

    @Test
    @DisplayName("Service - should import a statement")
    public void importStatementWithUser() {
        var command = this.makeImportStatementListCommand();
        var client = this.makeClient();

        Mockito.when(statementRepository.saveAll(Mockito.anyList())).thenReturn(null);
        Mockito.doReturn(client).when(clientRepository).findByDocument(Mockito.anyString());

        var response = importStatementsListUseCase.execute(command);

        Assertions.assertThat(response.get("message")).isEqualTo("Todos as transações foram importadas com sucesso!");
        Mockito.verify(statementRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(clientRepository, Mockito.times(2)).findByDocument(Mockito.anyString());
        Mockito.verify(clientRepository, Mockito.never()).save(Mockito.any(Client.class));
    }

    @Test
    @DisplayName("Service - should save the two user and statement together")
    public void importStatementWithoutUser() throws Exception {
        var command = this.makeImportStatementListCommand();
        var statement = this.makeStatement();
        Mockito.when(statementRepository.saveAll(List.of(statement))).thenReturn(null);
        Mockito.when(clientRepository.findByDocument(statement.getClient().getDocument())).thenReturn(null);

        var response = importStatementsListUseCase.execute(command);

        Assertions.assertThat(response.get("message")).isEqualTo("Todos as transações foram importadas com sucesso!");
        Mockito.verify(statementRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(clientRepository, Mockito.times(2)).findByDocument(Mockito.anyString());
        Mockito.verify(clientRepository, Mockito.times(2)).save(Mockito.any(Client.class));
    }
}
