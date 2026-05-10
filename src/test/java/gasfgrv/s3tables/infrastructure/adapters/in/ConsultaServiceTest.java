package gasfgrv.s3tables.infrastructure.adapters.in;

import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.domain.ports.out.TabelaRepositoryPort;
import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import gasfgrv.s3tables.mocks.GenerateMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock
    private TabelaRepositoryPort tabelaRepositoryPort;

    @Mock
    private TableMapper mapper;

    @InjectMocks
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deve consultar o repositório e mapear o resultado para o domínio com sucesso")
    void deveConsultarTabelaEMapearComSucesso() {
        // Arrange
        TableDto objetoInfra = GenerateMockData.generateDto();
        DadosTabela dadosDominioEsperados = GenerateMockData.generateDomain();

        when(tabelaRepositoryPort.consultarTabela()).thenReturn(objetoInfra);
        when(mapper.toDomain(objetoInfra)).thenReturn(dadosDominioEsperados);

        // Act
        DadosTabela resultado = consultaService.consultarTabela();

        // Assert
        assertNotNull(resultado);

        // Verificações de interação
        verify(tabelaRepositoryPort).consultarTabela();
        verify(mapper).toDomain(objetoInfra);
    }

}
