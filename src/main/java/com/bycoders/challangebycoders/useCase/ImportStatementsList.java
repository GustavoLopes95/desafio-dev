package com.bycoders.challangebycoders.useCase;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.protobuf.StatementsProtos;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImportStatementsList {


    public Map<String, String> execute(ImportStatementListCommand command) {
        return null;
    }
}
