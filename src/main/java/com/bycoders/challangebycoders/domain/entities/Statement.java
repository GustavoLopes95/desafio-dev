package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.domain.factories.OperationFactory;
import com.bycoders.challangebycoders.domain.valueObject.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
public class Statement extends DomainEntity {

    @Column(name = "client_id")
    private Client client;

    @Column(name = "operation_type")
    private Integer type;

    private String description;

    private Double value;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "accounting_balance")
    private Double accountingBalance;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "security_hash")
    private String securityHash;

    @CreationTimestamp
    private Instant createdAt;


    public Statement(Long id, Client client, Integer type, Double value, java.lang.String cardNumber, LocalDateTime transactionDate) {
        super(id);
        this.client = client;
        this.type = type;
        this.value = value;
        this.cardNumber = cardNumber;
        this.transactionDate = transactionDate;

        this.addValue(value);
    }

    public Statement(Client client, OperationType operationType, Double value, String cardNumber, LocalDateTime transactionDate) {
        super();
        this.client = client;
        this.type = operationType.getId();
        this.cardNumber = cardNumber;
        this.transactionDate = transactionDate;
//        this.accountingBalance = accountingBalance;
//        this.securityHash = securityHash;

        this.addValue(value);
    }

    public void addValue(Double value) {
        this.value = value/100.00;
    }

    public OperationType getType() {
        return OperationFactory.make(OperationTypeEnum.valueOf(this.type));
    }

    public boolean isCredit() {
        return this.getType().getIsCredit();
    }

    public Boolean isValid() {
        if(Objects.isNull(type)) {
            this.errors.addErrors("Tipo da operação", "Não pode estar vazio ou contem um valor invalido, favor usar de 1-9");
        }

        if(Double.isNaN(value)) {
            this.errors.addErrors("Valor", "Valor de transação invalido, favor seguir o padrão ex: R$1,20, 0000000120");
        }

        if(cardNumber.isEmpty() || cardNumber.length() < 8) {
            this.errors.addErrors("Cartão de credito", "Não pode estar vazio ou é um numero inválido");
        }

        if(Objects.isNull(transactionDate)) {
            this.errors.addErrors("Data da ocorrência", "Não pode estar vazia ou esta em um padrão invalido, favor utilizar 'yyyyMMdd HHmmss'");
        }

        return this.errors.isValid();
    }
}
