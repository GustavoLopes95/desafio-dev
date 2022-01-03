package com.bycoders.challangebycoders.repositories.adapters;

import com.bycoders.challangebycoders.domain.entities.Statement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStatementRepositoryJpaAdapter extends JpaRepository<Statement, Long> {
    List<Statement> findByClientId(Long clientId);
}
