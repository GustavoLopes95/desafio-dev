package com.bycoders.challangebycoders.useCase;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.domain.factories.OperationFactory;
import com.bycoders.protobuf.StatementsProtos;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImportStatementsListUseCase {

    public Map<String, String> execute(ImportStatementListCommand command) {
        this.parse(command.getStatements());
        return Map.of("message", "Todos as transações foram importadas com sucesso!");
    }

    private List<Statement> parse(List<String> statements) {
        return statements.stream().map(statement -> {
            var operationType = OperationFactory.make(OperationTypeEnum.valueOf(Integer.valueOf(statement.substring(0, 1))));
            var transactionDate = LocalDateTime.parse(statement.substring(1, 9).concat(" ").concat(statement.substring(42, 48)), DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"));
            var value = Double.valueOf(statement.substring(9, 18));
            var cardNumber = statement.substring(30, 41);

            var document = statement.substring(19, 29);
            var client = statement.substring(48, 61);
            var clientName = statement.substring(62, 80);

            var entity = new Statement(1L, operationType, value, cardNumber, transactionDate);
            return entity;
        }).collect(Collectors.toList());
    }
}
