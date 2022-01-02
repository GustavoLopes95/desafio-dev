package com.bycoders.challangebycoders.domain.entities;

import com.bycoders.challangebycoders.core.domainObject.DomainEntity;
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

@Entity
@Getter
@NoArgsConstructor
public class Statement extends DomainEntity {

    @Column(name = "client_id")
    private Long clientId;

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

    public Statement(Long clientId, OperationType operationType, Double value, String cardNumber, LocalDateTime transactionDate) {
        this.clientId = clientId;
        this.type = operationType.getId();
//        this.description = description;
        this.cardNumber = cardNumber;
        this.transactionDate = transactionDate;
//        this.accountingBalance = accountingBalance;
//        this.securityHash = securityHash;

        this.addValue(value);
    }

    public void addValue(Double value) {
        if(value == 0) {
            this.value = 0.00;
            return;
        }
        this.value = value/100.00;
    }

    public OperationType getType() {
        return OperationFactory.make(OperationTypeEnum.valueOf(this.type));
    }

    public void setType(OperationType type) {
        this.type = type.getId();
    }

    public boolean isCredit() {
        return this.getType().getIsCredit();
    }
}
