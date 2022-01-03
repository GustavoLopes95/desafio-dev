package com.bycoders.challangebycoders.repositories;


import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.interfaces.IStatementRepository;
import com.bycoders.challangebycoders.repositories.adapters.IClientRepositoryJpaAdapter;
import com.bycoders.challangebycoders.repositories.adapters.IStatementRepositoryJpaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public class StatementRepository implements IStatementRepository {

    @Autowired
    private IStatementRepositoryJpaAdapter jpaAdapter;

    @Override
    public void saveAll(List<Statement> statements) {
        jpaAdapter.saveAll(statements);
    }
}
