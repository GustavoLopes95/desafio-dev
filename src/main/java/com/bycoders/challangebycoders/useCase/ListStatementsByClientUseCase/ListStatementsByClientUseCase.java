package com.bycoders.challangebycoders.useCase.ListStatementsByClientUseCase;

import com.bycoders.challangebycoders.repositories.ClientBalanceRepository;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListStatementsByClientUseCase {

    private StatementRepository statementRepository;
    private ClientBalanceRepository balanceRepository;

    @Autowired
    public ListStatementsByClientUseCase(StatementRepository statementRepository, ClientBalanceRepository balanceRepository) {
        this.statementRepository = statementRepository;
        this.balanceRepository = balanceRepository;
    }

    public ListStatementsByClientUseCaseResponse execute(Long clientId) {
        var statements = this.statementRepository.findAll(clientId);
        var client = statements.get(0).getClient();
        var balance = balanceRepository.findByClientId(client.getId());
        return new ListStatementsByClientUseCaseResponse(statements, balance.getValue(), client.getName());
    }
}
