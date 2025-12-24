package gasfgrv.s3tables.application;

import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import gasfgrv.s3tables.infrastructure.prop.AwsProperties;
import gasfgrv.s3tables.infrastructure.dto.TableDto;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3tables.S3TablesClient;
import software.amazon.awssdk.services.s3tables.model.GetTableRequest;
import software.amazon.awssdk.services.s3tables.model.GetTableResponse;

@Service
public class ConsultaService {

    private final AwsProperties awsProperties;
    private final S3TablesClient s3TablesClient;
    private final TableMapper mapper;

    public ConsultaService(AwsProperties awsProperties,
                           S3TablesClient s3TablesClient,
                           TableMapper mapper) {
        this.awsProperties = awsProperties;
        this.s3TablesClient = s3TablesClient;
        this.mapper = mapper;
    }

    public TableDto consultarTabela() {
        GetTableRequest tableRequest = GetTableRequest.builder()
                .tableArn(awsProperties.s3TableArn())
                .build();

        GetTableResponse tableResponse = s3TablesClient.getTable(tableRequest);

        return mapper.toDto(tableResponse);
    }

}
