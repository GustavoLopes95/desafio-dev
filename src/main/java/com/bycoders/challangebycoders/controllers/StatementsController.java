package com.bycoders.challangebycoders.controllers;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import com.bycoders.protobuf.StatementsProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/importStatements")
public class StatementsController {

    @Autowired
    private ImportStatementsListUseCase useCase;

    @PostMapping
    public ResponseEntity<Map<String, String>> importStatements(InputStream statements) throws Exception {
        var command = new ImportStatementListCommand(StatementsProtos.Statements.parseFrom(statements).getStatementList());
        var response= useCase.execute(command);
        return ResponseEntity.ok().body(response);
    }
}
