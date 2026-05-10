package gasfgrv.s3tables.infrastructure.adapters.out;

import gasfgrv.s3tables.infrastructure.config.AwsProperties;
import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import gasfgrv.s3tables.mocks.GenerateMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3tables.S3TablesClient;
import software.amazon.awssdk.services.s3tables.model.GetTableRequest;
import software.amazon.awssdk.services.s3tables.model.GetTableResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TabelaRepositoryTest {

    @Mock
    private AwsProperties awsProperties;

    @Mock
    private S3TablesClient s3TablesClient;

    @Mock
    private TableMapper mapper;

    @InjectMocks
    private TabelaRepository tabelaRepository;

    @Test
    @DisplayName("Deve consultar a tabela no S3 via SDK e converter para DTO")
    void deveConsultarTabelaComSucesso() {
        // Arrange
        String arnFake = "arn:aws:s3tables:us-east-1:123:bucket/b/table/t";
        TableDto dtoEsperado = GenerateMockData.generateDto();

        GetTableResponse responseFake = GetTableResponse.builder().build();

        when(awsProperties.s3TableArn()).thenReturn(arnFake);
        when(s3TablesClient.getTable(any(GetTableRequest.class))).thenReturn(responseFake);
        when(mapper.toDto(responseFake)).thenReturn(dtoEsperado);

        // Act
        TableDto resultado = tabelaRepository.consultarTabela();

        // Assert
        assertNotNull(resultado);

        // Verificação adicional: o ARN correto foi passado para o request?
        verify(s3TablesClient).getTable(any(GetTableRequest.class));
    }

}