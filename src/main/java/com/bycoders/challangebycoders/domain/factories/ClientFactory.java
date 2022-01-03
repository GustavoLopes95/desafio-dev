package com.bycoders.challangebycoders.domain.factories;

import com.bycoders.challangebycoders.domain.entities.Client;
import com.bycoders.challangebycoders.domain.enums.DocumentTypeEnum;

public class ClientFactory {

    public static Client make(String clientString) {
        var document = clientString.substring(19, 29);
        var billingContactName = clientString.substring(48, 62).trim();
        var corporateName = clientString.substring(62, 80).trim();

        return new Client(corporateName, document, DocumentTypeEnum.CPF, billingContactName);
    }
}
