package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
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

    @Column(name = "billing_contact_name")
    public String billingContactName;

    public Client(Long id,  String corporateName, String document, DocumentTypeEnum documentType) {
        super(id);
        this.name = corporateName;
        this.document = document;
        this.documentType = documentType.getCode();
    }

    public Client(String name, String document, DocumentTypeEnum documentType, String billingContactName) {
        this.name = name;
        this.document = document;
        this.documentType = documentType.getCode();
        this.billingContactName = billingContactName;
    }

    public DocumentTypeEnum getDocumentType() {
        return DocumentTypeEnum.valueOf(this.documentType);
    }
}
