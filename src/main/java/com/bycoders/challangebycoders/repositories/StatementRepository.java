package com.bycoders.challangebycoders.repositories;


import com.bycoders.challangebycoders.domain.entities.Statement;
import com.bycoders.challangebycoders.domain.interfaces.IStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public abstract class StatementRepository implements IStatementRepository, JpaRepository<Statement, Long> {
}
