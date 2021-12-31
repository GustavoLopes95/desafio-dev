package com.bycoders.challangebycoders.controllers;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.useCase.ImportStatementsList;
import com.bycoders.protobuf.StatementsProtos;
import com.google.protobuf.ProtocolStringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/importStatements")
public class StatementsController {

    @Autowired
    private ImportStatementsList useCase;

    @PostMapping
    public ResponseEntity<Map<String, String>> importStatements(InputStream statements) throws Exception {
        var command = new ImportStatementListCommand(StatementsProtos.Statements.parseFrom(statements).getStatementList());
        var response= useCase.execute(command);
        return ResponseEntity.ok().body(response);
    }
}
