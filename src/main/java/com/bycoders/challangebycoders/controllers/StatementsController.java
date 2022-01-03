package com.bycoders.challangebycoders.controllers;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.core.messages.PagingResponse;
import com.bycoders.challangebycoders.core.messages.ResponsePageable;
import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import com.bycoders.challangebycoders.useCase.ListStatementsByClientUseCase.ListStatementsByClientUseCase;
import com.bycoders.challangebycoders.useCase.ListStatementsByClientUseCase.ListStatementsByClientUseCaseResponse;
import com.bycoders.protobuf.StatementsProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/statement")
public class StatementsController {

    @Autowired
    private ImportStatementsListUseCase importStatementsListUseCase;

    @Autowired
    private ListStatementsByClientUseCase listStatementsByClientUseCase;

    @GetMapping
    public ResponseEntity<ListStatementsByClientUseCaseResponse> findAll(@RequestParam("clientId") Long clientId) {
        var pages = listStatementsByClientUseCase.execute(clientId);
        return ResponseEntity.ok().body(pages);
    }
}
