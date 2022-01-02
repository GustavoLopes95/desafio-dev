package com.bycoders.challangebycoders;

import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public ImportStatementsListUseCase importStatementsListUseCase() {
        return new ImportStatementsListUseCase();
    }
}
