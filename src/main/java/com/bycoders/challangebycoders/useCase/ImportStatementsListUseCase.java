package com.bycoders.challangebycoders.useCase;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.factories.ClientFactory;
import com.bycoders.challangebycoders.domain.factories.StatementFactory;
import com.bycoders.challangebycoders.repositories.ClientRepository;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImportStatementsListUseCase {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Transactional
    public Map<String, String> execute(ImportStatementListCommand command) {
        var statements = this.parse(command.getStatements());
        this.statementRepository.saveAll(statements);
        this.sendNotification(statements);
        return Map.of("message", "Todos as transações foram importadas com sucesso!");
    }

    private List<Statement> parse(List<String> statements) {
        return statements.stream().map(statement -> {
            var client = ClientFactory.make(statement);
            client = this.findOrCreateClient(client);
            return StatementFactory.make(statement, client);
        }).collect(Collectors.toList());
    }

    private Client findOrCreateClient(Client client) {
        var entity = clientRepository.findByDocument(client.getDocument());
        if(Objects.isNull(entity)) {
            return clientRepository.save(client);
        }

        return entity;
    }

    private void sendNotification(List<Statement> statements) {
        // Send notification
    }
}
