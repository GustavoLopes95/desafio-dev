package com.bycoders.challangebycoders.useCase.ListStatementsByClientUseCase;

import com.bycoders.challangebycoders.domain.interfaces.IClientBalanceRepository;
import com.bycoders.challangebycoders.domain.interfaces.IStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListStatementsByClientUseCase {

    private IStatementRepository statementRepository;
    private IClientBalanceRepository balanceRepository;

    @Autowired
    public ListStatementsByClientUseCase(IStatementRepository statementRepository, IClientBalanceRepository balanceRepository) {
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
