package com.bycoders.challangebycoders.repositories;

import com.bycoders.challangebycoders.domain.entities.ClientBalance;
import com.bycoders.challangebycoders.domain.interfaces.IClientBalanceRepository;
import com.bycoders.challangebycoders.repositories.adapters.IClientBalanceRepositoryJpaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientBalanceRepository implements IClientBalanceRepository {

    @Autowired
    private IClientBalanceRepositoryJpaAdapter jpaAdapter;

    @Override
    public ClientBalance save(ClientBalance balance) {
        return jpaAdapter.save(balance);
    }

    @Override
    public ClientBalance update(ClientBalance balance) {
        return jpaAdapter.save(balance);
    }

    @Override
    public ClientBalance findByClientId(Long id) {
        return jpaAdapter.findByClientId(id).orElse(null);
    }
}
