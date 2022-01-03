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
        var statementsList = this.parse(command.getStatements());
        var statements = statementsList.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
        this.statementRepository.saveAll(statements);
        this.updateClientBalance(statements);
        this.sendNotification(statements);

        if(!this.errors.isEmpty()) {
            return Map.of("message", "Operação parcialmente concluida!", "errors", this.errors);
        }

        return Map.of("message", "Todos as transações foram importadas com sucesso!");
    }

    private List<Statement> parse(List<String> statements) {
        return statements.stream().map(statementInput -> {
            this.index += 1;
            var client = ClientFactory.make(statementInput);

            if(!client.isValid()) this.errors.add(Map.of("Linha " + this.index, client.getErrors()));
            client = this.findOrCreateClient(client);

            var statement = StatementFactory.make(statementInput, client);
            if(!statement.isValid()) {
                this.errors.add(Map.of("Linha " + this.index, statement.getErrors()));
                return null;
            }

            return statement;
        }).collect(Collectors.toList());
    }

    private Client findOrCreateClient(Client client) {
        if(!client.isValid()) return null;

        var entity = clientRepository.findByDocument(client.getDocument());
        if(Objects.isNull(entity)) {
            clientRepository.save(client);
            clientBalanceRepository.save(new ClientBalance(client, 0.00));
            return client;
        }

        return entity;
    }

    private void updateClientBalance(List<Statement> statements) {
        statements.stream().forEach(statement -> {
            var balance = clientBalanceRepository.findByClientId(statement.getClient().getId());
            if(statement.getType().getIsCredit()) balance.incrementValue(statement.getValue());
            else if(!statement.getType().getIsCredit()) balance.decrementValue(statement.getValue());

            clientBalanceRepository.update(balance);
        });
    }

    private void sendNotification(List<Statement> statements) {
        // Send notification
    }
}
