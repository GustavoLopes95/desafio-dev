package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;
import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.domain.factories.OperationFactory;
import com.bycoders.challangebycoders.domain.valueObject.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Statement extends DomainEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "operation_type")
    private Integer type;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "accounting_balance", precision = 10, scale = 2)
    private BigDecimal accountingBalance;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;


    @CreationTimestamp
    private Instant createdAt;


    public Statement(Long id, Client client, Integer type, BigDecimal value, String cardNumber, LocalDateTime transactionDate, BigDecimal accountingBalance) {
        super(id);
        this.client = client;
        this.type = type;
        this.value = value;
        this.cardNumber = cardNumber;
        this.transactionDate = transactionDate;
        this.accountingBalance = accountingBalance;

        this.addValue(value);
    }

    public Statement(Client client, OperationType operationType, BigDecimal value, String cardNumber, LocalDateTime transactionDate, BigDecimal accountingBalance) {
        super();
        this.client = client;
        this.type = operationType.getId();
        this.cardNumber = cardNumber;
        this.transactionDate = transactionDate;
        this.accountingBalance = accountingBalance;

        this.addValue(value);
    }

    public void addValue(BigDecimal value) {
        this.value = value.divide(BigDecimal.valueOf(100.00), 2, RoundingMode.UNNECESSARY);
    }

    public OperationType getType() {
        return OperationFactory.make(OperationTypeEnum.valueOf(this.type));
    }

    @JsonIgnore
    public boolean isCredit() {
        return this.getType().getIsCredit();
    }

    @JsonIgnore
    public Boolean isValid() {
        if(Objects.isNull(type)) {
            this.errors.addErrors("Tipo da operação", "Não pode estar vazio ou contem um valor invalido, favor usar de 1-9");
        }

        if(Objects.isNull(value)) {
            this.errors.addErrors("Valor", "Valor de transação invalido, favor seguir o padrão ex: R$1,20, 000001200");
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
