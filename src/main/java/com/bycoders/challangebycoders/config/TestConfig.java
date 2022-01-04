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
                .addStatement("2201903010000010700845152540738723****9987123333MARCOS PEREIRAMERCADO DA AVENIDA")
                .addStatement("2201903010000050200845152540738473****1231231233MARCOS PEREIRAMERCADO DA AVENIDA")
                .addStatement("3201903010000060200232702980566777****1313172712JOSÉ COSTA    MERCEARIA 3 IRMÃOS")
                .addStatement("1201903010000020000556418150631234****3324090002MARIA JOSEFINALOJA DO Ó - MATRIZ")
                .addStatement("5201903010000080200845152540733123****7687145607MARCOS PEREIRAMERCADO DA AVENIDA")
                .addStatement("2201903010000010200232702980568473****1231231233JOSÉ COSTA    MERCEARIA 3 IRMÃOS")
                .addStatement("3201903010000610200232702980566777****1313172712JOSÉ COSTA    MERCEARIA 3 IRMÃOS")
                .addStatement("4201903010000015232556418150631234****6678100000MARIA JOSEFINALOJA DO Ó - FILIAL")
                .addStatement("8201903010000010203845152540732344****1222123222MARCOS PEREIRAMERCADO DA AVENIDA")
                .addStatement("3201903010000010300232702980566777****1313172712JOSÉ COSTA    MERCEARIA 3 IRMÃOS")
                .addStatement("9201903010000010200556418150636228****9090000000MARIA JOSEFINALOJA DO Ó - MATRIZ")
                .addStatement("4201906010000050617845152540731234****2231100000MARCOS PEREIRAMERCADO DA AVENIDA")
                .addStatement("2201903010000010900232702980568723****9987123333JOSÉ COSTA    MERCEARIA 3 IRMÃOS")
                .addStatement("8201903010000000200845152540732344****1222123222MARCOS PEREIRAMERCADO DA AVENIDA")
                .addStatement("2201903010000000500232702980567677****8778141808JOSÉ COSTA    MERCEARIA 3 IRMÃOS")
                .addStatement("3201903010000019200845152540736777****1313172712MARCOS PEREIRAMERCADO DA AVENIDA")
                .build();
        var useCase = new ImportStatementsListUseCase(clientRepository, statementRepository, clientBalanceRepository);
        var command = new ImportStatementListCommand(payload.getStatementList());
        useCase.execute(command);
    }
}
