package com.bycoders.challangebycoders.useCase;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.entities.ClientBalance;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.factories.ClientFactory;
import com.bycoders.challangebycoders.domain.factories.StatementFactory;
import com.bycoders.challangebycoders.core.domainObject.DomainValidateError;
import com.bycoders.challangebycoders.repositories.ClientBalanceRepository;
import com.bycoders.challangebycoders.repositories.ClientRepository;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImportStatementsListUseCase {

    private List<Map<String, DomainValidateError>> errors = new ArrayList<>();

    private ClientRepository clientRepository;
    private ClientBalanceRepository clientBalanceRepository;
    private StatementRepository statementRepository;

    @Autowired
    public ImportStatementsListUseCase(ClientRepository clientRepository, StatementRepository statementRepository, ClientBalanceRepository clientBalanceRepository) {
        this.clientRepository = clientRepository;
        this.statementRepository = statementRepository;
        this.clientBalanceRepository = clientBalanceRepository;
    }

    private Integer index = -1;

    @Transactional
    public Map<String, Object> execute(ImportStatementListCommand command) {
        var statements = command.getStatements().stream().map(statementInput -> {
            var statement = this.parse(statementInput);
            if(Objects.isNull(statement)) return null;

            this.statementRepository.save(statement);
            this.updateClientBalance(statement);

            return statement;
        }).collect(Collectors.toList());

        this.sendNotification(statements);

        if(!this.errors.isEmpty()) {
            return Map.of("message", "Operação parcialmente concluida!", "errors", this.errors);
        }

        return Map.of("message", "Todos as transações foram importadas com sucesso!");
    }

    private Statement parse(String statementInput) {
            this.index += 1;
            var client = ClientFactory.make(statementInput);

            if(!client.isValid()) this.errors.add(Map.of("Linha " + this.index, client.getErrors()));
            client = this.findOrCreateClient(client);

            var balance = this.getClientBalance(client);
            var statement = StatementFactory.make(statementInput, client, balance);

            if(!statement.isValid()) {
                this.errors.add(Map.of("Linha " + this.index, statement.getErrors()));
                return null;
            }

            return statement;
    }

    private Client findOrCreateClient(Client client) {
        if(!client.isValid()) return null;

        var entity = clientRepository.findByDocument(client.getDocument());
        if(Objects.isNull(entity)) {
            clientRepository.save(client);
            clientBalanceRepository.save(new ClientBalance(client, BigDecimal.valueOf(0.00)));
            return client;
        }

        return entity;
    }

    private void updateClientBalance(Statement statement) {
        var balance = clientBalanceRepository.findByClientId(statement.getClient().getId());
        if(statement.getType().getIsCredit()) balance.incrementValue(statement.getValue());
        else if(!statement.getType().getIsCredit()) balance.decrementValue(statement.getValue());

        clientBalanceRepository.update(balance);
    }

    private ClientBalance getClientBalance(Client client) {
        if(Objects.isNull(client) || !client.isValid()) return null;

        return clientBalanceRepository.findByClientId(client.getId());
    }

    private void sendNotification(List<Statement> statements) {
        // Send notification
    }
}
