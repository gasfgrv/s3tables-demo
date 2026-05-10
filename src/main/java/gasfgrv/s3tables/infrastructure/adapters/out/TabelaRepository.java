package gasfgrv.s3tables.infrastructure.adapters.out;

import gasfgrv.s3tables.domain.ports.out.TabelaRepositoryPort;
import gasfgrv.s3tables.infrastructure.config.AwsProperties;
import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3tables.S3TablesClient;
import software.amazon.awssdk.services.s3tables.model.GetTableRequest;

@Component
public class TabelaRepository implements TabelaRepositoryPort {

    private final AwsProperties awsProperties;
    private final S3TablesClient s3TablesClient;
    private final TableMapper mapper;

    public TabelaRepository(AwsProperties awsProperties, S3TablesClient s3TablesClient, TableMapper mapper) {
        this.awsProperties = awsProperties;
        this.s3TablesClient = s3TablesClient;
        this.mapper = mapper;
    }

    @Override
    public TableDto consultarTabela() {
        GetTableRequest tableRequest = GetTableRequest.builder()
                .tableArn(awsProperties.s3TableArn())
                .build();
        return mapper.toDto(s3TablesClient.getTable(tableRequest));
    }

}
