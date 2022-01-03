package com.bycoders.challangebycoders.domain.factories;

import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatementFactory {

    public static Statement make(String statement, Client client) {
        var operationType = OperationFactory.make(OperationTypeEnum.valueOf(Integer.valueOf(statement.substring(0, 1))));
        var transactionDate = LocalDateTime.parse(statement.substring(1, 9).concat(" ").concat(statement.substring(42, 48)), DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"));
        var value = Double.valueOf(statement.substring(9, 18));
        var cardNumber = statement.substring(30, 41);

        return  new Statement(client, operationType, value, cardNumber, transactionDate);
    }
}
