package com.bycoders.challangebycoders.domain.factories;

import com.bycoders.challangebycoders.domain.enums.OperationTypeEnum;
import com.bycoders.challangebycoders.domain.valueObject.OperationType;

public class OperationFactory {

    public static OperationType make(OperationTypeEnum type) {
        switch (type) {
            case DEBITO:
                return new OperationType(1, "Débito", true);
            case BOLETO:
                return new OperationType(2, "Boleto", false);
            case FINANCIAMENTO:
                return new OperationType(3, "Financiamento", false);
            case CREDITO:
                return new OperationType(4, "Crédito", true);
            case RECEBIMENTO_EMPRESTIMO:
                return new OperationType(5, "Recebimento Empréstimo", true);
            case VENDAS:
                return new OperationType(6, "Vendas", true);
            case RECEBIMENTO_TED:
                return new OperationType(7, "Recebimento TED", true);
            case RECEBIMENTO_DOC:
                return new OperationType(8, "Recebimento DOC", true);
            case ALUGUEL:
                return new OperationType(9, "Aluguel", false);
            default:
                throw new RuntimeException("Not implement");
        }
    }
}
