package com.bycoders.challangebycoders.core.domainObject;

import com.bycoders.challangebycoders.exceptions.DomainException;
import lombok.Getter;

import java.util.*;

@Getter
public class DomainValidate extends DomainException {
    protected Map<String, List<String>> errors = new HashMap<>();

    public DomainValidate(String entity) {
        super(entity);
    }

    public void addErrors(String property, String message) {
        if(this.existsErrorsProperty(property)) this.getPropertyAndAddError(property, message);

        this.errors.put(property, new ArrayList<>());
        this.getPropertyAndAddError(property, message);
    }

    public Boolean existsErrorsProperty(String property) {
        return this.errors.containsKey(property);
    }

    public void getPropertyAndAddError(String property, String message) {
        var propertyErrors = this.errors.get(property);
        propertyErrors.add(message);
    }

    public boolean isValid() {
        return this.errors.isEmpty();
    }

    public Map<String, List<String>> getErrors() {
        var errors = Collections.unmodifiableMap(this.errors);
        this.clearAllErrors();
        return errors;
    }

    public void clearAllErrors() {
        this.errors = new HashMap<>();
    }
}
