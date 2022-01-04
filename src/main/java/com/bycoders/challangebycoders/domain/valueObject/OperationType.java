package com.bycoders.challangebycoders.domain.valueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Transient;

@Getter
@AllArgsConstructor
public class OperationType {
    private Integer id;
    private String name;

    @Transient
    private Boolean isCredit;
}
