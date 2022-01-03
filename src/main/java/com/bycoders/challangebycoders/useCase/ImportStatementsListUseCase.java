package com.bycoders.challangebycoders.useCase;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.domain.factories.OperationFactory;
import com.bycoders.protobuf.StatementsProtos;
import org.springframework.stereotype.Service;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
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
        return statements.stream().map(statement -> this.factory(statement)).collect(Collectors.toList());
    }

    private Statement factory(String statement) {
        var operationType = OperationFactory.make(OperationTypeEnum.valueOf(Integer.valueOf(statement.substring(0, 1))));
        var transactionDate = LocalDateTime.parse(statement.substring(1, 9).concat(" ").concat(statement.substring(42, 48)), DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"));
        var value = Double.valueOf(statement.substring(9, 18));
        var cardNumber = statement.substring(30, 41);

        var document = statement.substring(19, 29);


//        var client = statement.substring(48, 61);
        var client = this.extractStatement(statement, 48, 61);
//        var clientName = statement.substring(62, 80);
        var clientName = this.extractStatement(statement, 62, 80);
        return  new Statement(1L, operationType, value, cardNumber, transactionDate);
    }

    private String extractStatement(String statement, Integer startIndex, Integer maxIndex) {
        CharacterIterator it = new StringCharacterIterator(statement);
        it.setIndex(startIndex-1);
        var sb = new StringBuilder();
        while(it.getIndex() < maxIndex || Character.isWhitespace(it.current())){
            sb.append(it.next());
        }
        return sb.toString();
    }
}
