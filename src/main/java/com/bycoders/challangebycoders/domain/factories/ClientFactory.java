package com.bycoders.challangebycoders.domain.factories;

import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;
import com.bycoders.challangebycoders.utils.StringUtils;

import java.util.Objects;

public class ClientFactory {

    public static Client make(String cnab) {
        var document = StringUtils.subStringOrNull(cnab, 19, 29);
        var billingContactName = ClientFactory.applyTrim(StringUtils.subStringOrNull(cnab, 48, 62));
        var corporateName = ClientFactory.applyTrim(StringUtils.subStringOrNull(cnab, 62, 80));

        return new Client(corporateName, document, DocumentTypeEnum.CPF, billingContactName);
    }

    private static String applyTrim(String value) {
        if(!Objects.isNull(value)) return value.trim();
        return null;
    }
}
