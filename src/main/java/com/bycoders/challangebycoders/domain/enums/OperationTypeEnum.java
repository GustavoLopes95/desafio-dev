package com.bycoders.challangebycoders.domain.enums;

public enum OperationTypeEnum {
    DEBITO(1),
    BOLETO(2),
    FINANCIAMENTO(3),
    CREDITO(4),
    RECEBIMENTO_EMPRESTIMO(5),
    VENDAS(6),
    RECEBIMENTO_TED(7),
    RECEBIMENTO_DOC(8),
    ALUGUEL(9);

    private Integer code;

    OperationTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public static OperationTypeEnum valueOf(Integer code) {
        for(OperationTypeEnum type : OperationTypeEnum.values()) {
            if(type.getCode() == code) return type;
        }

        return null;
    }
}
