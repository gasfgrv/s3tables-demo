package gasfgrv.s3tables.service;

import gasfgrv.s3tables.config.AwsProperties;
import gasfgrv.s3tables.controller.dto.TableDto;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3tables.S3TablesClient;
import software.amazon.awssdk.services.s3tables.model.GetTableRequest;
import software.amazon.awssdk.services.s3tables.model.GetTableResponse;

@Service
public class ConsultaService {

    private final AwsProperties awsProperties;
    private final S3TablesClient s3TablesClient;

    public ConsultaService(AwsProperties awsProperties, S3TablesClient s3TablesClient) {
        this.awsProperties = awsProperties;
        this.s3TablesClient = s3TablesClient;
    }

    public TableDto consultarTabela() {
        GetTableRequest tableRequest = GetTableRequest.builder()
                .tableArn(awsProperties.getS3TableArn())
                .build();

        GetTableResponse tableResponse = s3TablesClient.getTable(tableRequest);

        return TableDto.toDto(tableResponse.toString());
    }

}
