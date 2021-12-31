package com.bycoders.challangebycoders.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DomainException extends RuntimeException {
    private String entity;

    public DomainException(String message, String entity) {
        super(message);
        this.entity = entity;
    }
}
