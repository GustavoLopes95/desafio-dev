package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends DomainEntity {

    @Column(name = "corporate_name")
    public String name;

    public String document;

    @Column(name = "document_type")
    public Integer documentType;

    public Client(Long id, String tradingName, String corporateName, String document, Integer documentType) {
        super(id);
        this.name = corporateName;
        this.document = document;
        this.documentType = documentType;
    }
}
