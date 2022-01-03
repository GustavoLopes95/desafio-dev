package com.bycoders.challangebycoders.repositories.adapters;

import com.bycoders.challangebycoders.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepositoryJpaAdapter extends JpaRepository<Client, Long> {
    Client findByDocument(String document);
}
