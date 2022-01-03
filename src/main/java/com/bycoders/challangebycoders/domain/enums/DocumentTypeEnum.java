package com.bycoders.challangebycoders.domain.enums;

import javax.print.Doc;

public enum DocumentTypeEnum {
    CPF(1),
    CNPJ(2);

    private Integer code;

    DocumentTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static DocumentTypeEnum valueOf(Integer code) {
        for (DocumentTypeEnum type: DocumentTypeEnum.values()) {
            if(type.getCode() == code) return type;
        }

        return null;
    }
}
