package com.bycoders.challangebycoders.repositories;

import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.interfaces.IStatementRepository;
import com.bycoders.challangebycoders.repositories.adapters.IStatementRepositoryJpaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StatementRepository implements IStatementRepository {

    @Autowired
    private IStatementRepositoryJpaAdapter jpaAdapter;

    public Statement save(Statement statement) {
        return jpaAdapter.save(statement);
    }

    @Override
    public List<Statement> findAll(Long clientId) {
        return jpaAdapter.findByClientId(clientId);
    }

    @Override
    public void saveAll(List<Statement> statements) {
        jpaAdapter.saveAll(statements);
    }

}
