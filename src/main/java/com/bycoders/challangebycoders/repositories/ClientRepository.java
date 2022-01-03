package com.bycoders.challangebycoders.repositories;

import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.interfaces.IClientRepository;
import com.bycoders.challangebycoders.repositories.adapters.IClientRepositoryJpaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository implements IClientRepository {

    @Autowired
    private IClientRepositoryJpaAdapter jpaAdapter;

    @Override
    public Client findByDocument(String document) {
        return jpaAdapter.findByDocument(document);
    }

    @Override
    public Client save(Client client) {
        return jpaAdapter.save(client);
    }
}
