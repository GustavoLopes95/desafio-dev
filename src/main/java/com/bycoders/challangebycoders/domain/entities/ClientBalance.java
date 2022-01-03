package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.exceptions.DomainException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class ClientBalance {

    @Id
    @Column(name = "client_id", nullable = false, unique = true)
    private Client client;

    private Double value;

    public ClientBalance(Client client, Double value) {
        this.client = client;
        this.value = value;
    }

    public void incrementValue(Double value) {
        if(value < 0) {
            value = value * -1;
        }
        this.value = value;
    }

    public void decrementValue(Double value) {
        if(value > 0) {
            value = value * -1;
        }
        this.value = value;
    }
}
