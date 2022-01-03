package com.bycoders.challangebycoders.repositories;

import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.interfaces.IClientRepository;
import com.bycoders.challangebycoders.repositories.adapters.ClientRepositoryJpaAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientRepository implements IClientRepository {

    @Autowired
    private ClientRepositoryJpaAdapter jpaAdapter;

    @Override
    public Client findById(Long id) {
        return jpaAdapter.findById(id)
                .orElse(null);
    }

    @Override
    public Client findByDocument(String document) {
        return jpaAdapter.findByDocument(document);
    }

    @Override
    public Client save(Client client) {
        return jpaAdapter.save(client);
    }
}
