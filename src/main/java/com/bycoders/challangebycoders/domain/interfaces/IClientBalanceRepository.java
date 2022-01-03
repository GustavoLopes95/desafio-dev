package com.bycoders.challangebycoders.domain.interfaces;

import com.bycoders.challangebycoders.domain.entities.ClientBalance;

public interface IClientBalanceRepository {
    ClientBalance save(ClientBalance balance);
    ClientBalance update(ClientBalance balance);
    ClientBalance findByClientId(Long id);
}
