package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Client extends DomainEntity {

    @Column(name = "corporate_name")
    public String name;

    public String document;

    @Column(name = "document_type")
    @Getter(AccessLevel.NONE)
    public Integer documentType;

    @Column(name = "billing_contact_name")
    public String billingContactName;

    public Client(Long id,  String corporateName, String document, DocumentTypeEnum documentType, String billingContactName) {
        super(id);
        this.name = corporateName;
        this.document = document;
        this.documentType = documentType.getCode();
        this.billingContactName = billingContactName;
    }

    public Client(String name, String document, DocumentTypeEnum documentType, String billingContactName) {
        super();
        this.name = name;
        this.document = document;
        this.documentType = documentType.getCode();
        this.billingContactName = billingContactName;
    }

    public DocumentTypeEnum getDocumentType() {
        return DocumentTypeEnum.valueOf(this.documentType);
    }

    public Boolean isValid() {
        if(Objects.isNull(name) || name.isEmpty()) {
            this.errors.addErrors("nome", "Não pode estar vazio");
        }

        if(Objects.isNull(billingContactName) || billingContactName.isEmpty()) {
            this.errors.addErrors("Dono da loja", "Não pode estar vazio");
        }

        if(Objects.isNull(document) || this.getDocumentType().equals(DocumentTypeEnum.CPF) && document.isEmpty()) {
            this.errors.addErrors("CPF", "Não pode estar vazio");
        }

        return this.errors.isValid();
    }

}
