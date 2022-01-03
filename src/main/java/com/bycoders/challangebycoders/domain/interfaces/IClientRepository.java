package com.bycoders.challangebycoders.domain.interfaces;

import com.bycoders.challangebycoders.domain.entities.Client;

public interface IClientRepository {
    Client findByDocument(String document);
    Client findById(Long id);
    Client save(Client client);
}
