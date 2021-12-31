package com.bycoders.challangebycoders.controllers;

import com.bycoders.protobuf.StatementsProtos;
import com.google.protobuf.ProtocolStringList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/importStatements")
public class StatementsController {

    @GetMapping
    public ResponseEntity<Map<String,String>> test() {
        return ResponseEntity.ok().body( Map.of("message","hello wrold"));
    }

    @PostMapping
    public ResponseEntity<ProtocolStringList> importStatements(StatementsProtos.Statements statements) {
        var list = statements.getStatementList();
        return ResponseEntity.ok().body(list);
    }
}
