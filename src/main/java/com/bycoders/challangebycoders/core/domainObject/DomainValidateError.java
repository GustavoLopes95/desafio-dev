package com.bycoders.challangebycoders.core.domainObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class DomainValidateError {
    private String entity;

    @Getter(AccessLevel.NONE)
    private Map<String, List<String>> errors;

    public Map<String, List<String>> getErrors() {
        return Collections.unmodifiableMap(errors);
    }
}
