package com.bycoders.challangebycoders.config;

import com.bycoders.challangebycoders.commands.ImportStatementListCommand;
import com.bycoders.challangebycoders.repositories.ClientBalanceRepository;
import com.bycoders.challangebycoders.repositories.ClientRepository;
import com.bycoders.challangebycoders.repositories.StatementRepository;
import com.bycoders.challangebycoders.useCase.ImportStatementsListUseCase;
import com.bycoders.protobuf.StatementsProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientBalanceRepository clientBalanceRepository;

    @Autowired
    private StatementRepository statementRepository;


    @Override
    public void run(String... args) throws Exception {
        var payload = StatementsProtos.Statements.newBuilder()
                .addStatement("3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       ")
                .addStatement("5201903010000013200556418150633123****7687145607MARIA JOSEFINALOJA DO Ó - MATRIZ")
                .addStatement("2201903010000011200096206760173648****0099234234JOÃO MACEDO   BAR DO JOÃO       ")
                .addStatement("1201903010000015200096206760171234****7890233000JOÃO MACEDO   BAR DO JOÃO       ")
                .build();
        var useCase = new ImportStatementsListUseCase(clientRepository, statementRepository, clientBalanceRepository);
        var command = new ImportStatementListCommand(payload.getStatementList());
        useCase.execute(command);
    }
}
