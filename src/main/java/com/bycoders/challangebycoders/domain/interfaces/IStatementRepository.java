package com.bycoders.challangebycoders.domain.interfaces;

import com.bycoders.challangebycoders.domain.entities.Statement;

import java.util.List;

public interface IStatementRepository {
    List<Statement> findAll(Long clientId);
    void saveAll(List<Statement> statements);
}
