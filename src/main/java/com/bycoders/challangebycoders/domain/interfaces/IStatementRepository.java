package com.bycoders.challangebycoders.domain.interfaces;

import com.bycoders.challangebycoders.domain.entities.Statement;

import java.util.List;

public interface IStatementRepository {
    List<Statement> findAll(Long clientId);
    Statement save(Statement statement);
    void saveAll(List<Statement> statements);
}
