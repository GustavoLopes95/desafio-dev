package com.bycoders.challangebycoders.useCase.ListStatementsByClientUseCase;

import com.bycoders.challangebycoders.domain.entities.Statement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListStatementsByClientUseCaseResponse {
    private List<Statement> statements;
    private String total;
    private String clientName;

    public void addStatement(Statement s) {
        this.statements.add(s);
    }
}
