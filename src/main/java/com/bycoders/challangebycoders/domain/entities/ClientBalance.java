package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class ClientBalance extends DomainEntity {

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    public ClientBalance(Long id, Client client, BigDecimal value) {
        super(id);
        this.client = client;
        this.value = value;
    }

    public ClientBalance(Client client, BigDecimal value) {
        super();
        this.client = client;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void incrementValue(BigDecimal value) {
        this.value = this.value.add(value);
    }

    public void decrementValue(BigDecimal value) {
        this.value = this.value.subtract(value);
    }
}
