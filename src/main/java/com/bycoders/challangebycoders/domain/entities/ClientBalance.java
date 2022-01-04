package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
import com.bycoders.challangebycoders.exceptions.DomainException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Getter
@NoArgsConstructor
public class ClientBalance extends DomainEntity {

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

    private Double value;

    public ClientBalance(Long id, Client client, Double value) {
        super(id);
        this.client = client;
        this.value = value;
    }

    public ClientBalance(Client client, Double value) {
        super();
        this.client = client;
        this.value = value;
    }

    public String getValue() {
        return new DecimalFormat("#.##").format(value);
    }

    public void incrementValue(Double value) {
        if(value < 0) {
            value = value * -1;
        }
        this.value += value;
    }

    public void decrementValue(Double value) {
        if(value > 0) {
            value -= value * -1;
        }
        this.value = value;
    }
}
