package com.bycoders.challangebycoders.domain.factories;

import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.entities.ClientBalance;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.utils.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatementFactory {

    public static Statement make(String cnab, Client client, ClientBalance balance) {
        var operationType = OperationFactory.make(OperationTypeEnum.valueOf(Integer.valueOf(StringUtils.subStringOrNull(cnab,0, 1))));
        var transactionDate = StatementFactory.parseDatetime(cnab);
        var value = new BigDecimal(StringUtils.subStringOrNull(cnab,9, 18));
        var cardNumber = StringUtils.subStringOrNull(cnab,30, 41);

        return  new Statement(client, operationType, value, cardNumber, transactionDate, balance.getValue());
    }

    private static LocalDateTime parseDatetime(String cnab) {
        try {
            return LocalDateTime.parse(cnab.substring(1, 9).concat(" ").concat(cnab.substring(42, 48)), DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"));
        } catch (Exception e) {
            return null;
        }
    }
}
