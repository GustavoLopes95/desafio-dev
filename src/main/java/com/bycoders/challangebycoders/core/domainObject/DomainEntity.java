package com.bycoders.challangebycoders.core.domainObject;

import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@MappedSuperclass
public abstract class DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(AccessLevel.NONE)
    protected DomainValidate errors;

    public DomainEntity(Long id) {
        this.id = id;
        this.errors = new DomainValidate(this.getClass().getName());
    }

    public DomainEntity() {
        this.errors = new DomainValidate(this.getClass().getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEntity that = (DomainEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public DomainValidateError getErrors() {
        return new DomainValidateError(this.getClass().getName(), this.errors.getErrors());
    }
}
