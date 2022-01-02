package com.bycoders.challangebycoders.domain.valueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperationType {
    private Integer id;
    private String name;
    private Boolean isCredit;
}
