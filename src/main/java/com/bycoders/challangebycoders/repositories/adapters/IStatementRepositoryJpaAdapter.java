package com.bycoders.challangebycoders.repositories.adapters;

import com.bycoders.challangebycoders.domain.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatementRepositoryJpaAdapter extends JpaRepository<Statement, Long> {
}
