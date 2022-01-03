package com.bycoders.challangebycoders.repositories.adapters;

import com.bycoders.challangebycoders.domain.entities.ClientBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClientBalanceRepositoryJpaAdapter extends JpaRepository<ClientBalance, Long> {
    Optional<ClientBalance> findByClientId(Long id);
}
