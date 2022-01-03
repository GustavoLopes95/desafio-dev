package com.bycoders.challangebycoders;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.entities.ClientBalance;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.domain.factories.OperationFactory;
import com.bycoders.challangebycoders.useCase.ListStatementsByClientUseCase.ListStatementsByClientUseCaseResponse;
import com.bycoders.protobuf.StatementsProtos;
import org.hibernate.id.GUIDGenerator;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestBase {

    protected StatementsProtos.Statements makeProtoStatement() {
        return StatementsProtos.Statements.newBuilder()
                .addStatement("3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       ")
                .addStatement("5201903010000013200556418150633123****7687145607MARIA JOSEFINALOJA DO Ó - MATRIZ")
                .build();
    }

    protected StatementsProtos.Statements makeInvalidProtoStatement() {
        return StatementsProtos.Statements.newBuilder()
                .addStatement("3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       ")
                .addStatement("520010020055650633123****7687145607              LOJA DO Ó - MATRIZ")
                .build();
    }

    protected ImportStatementListCommand makeImportStatementListCommand() {
        var protoList = this.makeProtoStatement();
        return new ImportStatementListCommand(protoList.getStatementList());
    }

    protected ImportStatementListCommand makeImportInvalidStatementListCommand() {
        var protoList = this.makeInvalidProtoStatement();
        return new ImportStatementListCommand(protoList.getStatementList());
    }

    protected Statement makeStatement() throws Exception {
        var client = this.makeClient();
        var value = 0000014200.0;
        var balance = 0.00;
        var now = Instant.now();
        var operationType = OperationFactory.make(OperationTypeEnum.valueOf(1));

        var md = MessageDigest.getInstance("MD5");
        md.update("".concat(String.valueOf(value)).concat(String.valueOf(balance)).concat(now.toString()).getBytes());
        var digest = md.digest();
        var hash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        var date = LocalDateTime.parse("20190301 153453", DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"));
        return new Statement(client, operationType, value, "****3153", date);
    }

    protected Client makeClient() {
        return new Client(1L,"BAR DO JOÃO", "09620676017", DocumentTypeEnum.CPF, "João Macedo");
    }

    protected ClientBalance makeClientBalance() {
        var client = this.makeClient();
        return new ClientBalance(client, 0.00);
    }
}
