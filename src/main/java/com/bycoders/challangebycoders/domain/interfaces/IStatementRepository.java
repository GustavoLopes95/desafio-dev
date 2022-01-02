package com.bycoders.challangebycoders.domain.interfaces;

import com.bycoders.challangebycoders.domain.entities.Statement;
import org.springframework.data.jpa.domain.Specification;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface IStatementRepository {
    List<Statement> findAll(Specification<Statement> spec, Pageable pageable);
    Optional<Statement> findById(Long id);
}
