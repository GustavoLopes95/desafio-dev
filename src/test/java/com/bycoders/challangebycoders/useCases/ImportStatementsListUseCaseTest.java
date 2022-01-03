package com.bycoders.challangebycoders.useCases;

import com.bycoders.challangebycoders.TestBase;
import com.bycoders.challangebycoders.UseCasesConfig;
import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.entities.ClientBalance;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.repositories.ClientBalanceRepository;
import com.bycoders.challangebycoders.repositories.ClientRepository;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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

    @MockBean
    ClientBalanceRepository clientBalanceRepository;

    private ImportStatementsListUseCase importStatementsListUseCase;

    @BeforeEach
    @Autowired
    private void setup() {
        this.importStatementsListUseCase = new ImportStatementsListUseCase(clientRepository, statementRepository, clientBalanceRepository);
    }

    @Test
    @DisplayName("Service - should import a statement")
    public void importStatementWithUser() {
        var command = this.makeImportStatementListCommand();
        var client = this.makeClient();
        var balance = this.makeClientBalance();

        Mockito.mock(StatementRepository.class).saveAll(Mockito.anyList());
        Mockito.doReturn(client).when(clientRepository).findByDocument(Mockito.anyString());
        Mockito.doReturn(balance).when(clientBalanceRepository).save(Mockito.any(ClientBalance.class));
        Mockito.doReturn(balance).when(clientBalanceRepository).findByClientId(Mockito.anyLong());

        var response = importStatementsListUseCase.execute(command);

        Assertions.assertThat(response.get("message")).isEqualTo("Todos as transações foram importadas com sucesso!");
        Mockito.verify(statementRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(clientRepository, Mockito.times(2)).findByDocument(Mockito.anyString());
        // All client are already registered in database
        Mockito.verify(clientRepository, Mockito.never()).save(Mockito.any(Client.class));
        // All client already have balance
        Mockito.verify(clientBalanceRepository, Mockito.never()).save(Mockito.any(ClientBalance.class));
        // For each statement need update the client balance
        Mockito.verify(clientBalanceRepository, Mockito.times(2)).update(Mockito.any(ClientBalance.class));
    }

    @Test
    @DisplayName("Service - should save the two user and statement together")
    public void importStatementWithoutUser() throws Exception {
        var command = this.makeImportStatementListCommand();
        var statement = this.makeStatement();
        var client = Mockito.mock(Client.class);
        var balance = this.makeClientBalance();

        Mockito.mock(StatementRepository.class).saveAll(List.of(statement));
        Mockito.when(clientRepository.findByDocument(statement.getClient().getDocument())).thenReturn(null);

        Mockito.doReturn(statement.getClient()).when(clientRepository).save(Mockito.any(Client.class));
        Mockito.doReturn(balance).when(clientBalanceRepository).save(Mockito.any(ClientBalance.class));
        Mockito.doReturn(balance).when(clientBalanceRepository).findByClientId(Mockito.anyLong());

        var response = importStatementsListUseCase.execute(command);

        Assertions.assertThat(response.get("message")).isEqualTo("Todos as transações foram importadas com sucesso!");
        Mockito.verify(statementRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(clientRepository, Mockito.times(2)).findByDocument(Mockito.anyString());
        Mockito.verify(clientRepository, Mockito.times(2)).save(Mockito.any(Client.class));

        // Create client balance for the new clients
        Mockito.verify(clientBalanceRepository, Mockito.times(2)).save(Mockito.any(ClientBalance.class));
        // For each statement need update the client balance
        Mockito.verify(clientBalanceRepository, Mockito.times(2)).update(Mockito.any(ClientBalance.class));
    }

    @Test
    @DisplayName("Service - should save parcial statements and return errors messages")
    public void importStatementWitInvalidStatement() throws Exception {
        var command = this.makeImportInvalidStatementListCommand();
        var statement = this.makeStatement();
        var client = this.makeClient();
        var balance = this.makeClientBalance();

        Mockito.mock(StatementRepository.class).saveAll(List.of(statement));
        Mockito.doReturn(client).when(clientRepository).findByDocument(Mockito.anyString());
        Mockito.doReturn(balance).when(clientBalanceRepository).save(Mockito.any(ClientBalance.class));
        Mockito.doReturn(balance).when(clientBalanceRepository).findByClientId(Mockito.anyLong());

        var response = importStatementsListUseCase.execute(command);

        Assertions.assertThat(response.get("message")).isEqualTo("Operação parcialmente concluida!");
        Assertions.assertThat(response.get("errors")).isNotNull();

        Mockito.verify(statementRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(clientRepository, Mockito.times(1)).findByDocument(Mockito.anyString());

        // All client already have balance
        Mockito.verify(clientBalanceRepository, Mockito.never()).save(Mockito.any(ClientBalance.class));
        // For each statement need update the client balance, the other statement is invalid
        Mockito.verify(clientBalanceRepository, Mockito.times(1)).update(Mockito.any(ClientBalance.class));
    }
}
