package com.bycoders.challangebycoders.domain.interfaces;

import com.bycoders.challangebycoders.domain.entities.Client;

public interface IClientRepository {
    Client findByDocument(String document);
    Client save(Client client);
}
