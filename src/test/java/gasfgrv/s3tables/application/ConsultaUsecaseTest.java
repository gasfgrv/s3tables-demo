package gasfgrv.s3tables.application;

import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.domain.ports.in.ConsultaPort;
import gasfgrv.s3tables.mocks.GenerateMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaUsecaseTest {

    @Mock
    private ConsultaPort consultaPort;

    @InjectMocks
    private ConsultaUsecase consultaUsecase;

    @Test
    @DisplayName("Deve retornar dados da tabela com sucesso quando o port for chamado")
    void deveRetornarDadosTabelaComSucesso() {
        // Arrange (Preparação)
        DadosTabela dadosEsperados = GenerateMockData.generateDomain();
        when(consultaPort.consultarTabela()).thenReturn(dadosEsperados);

        // Act (Ação)
        DadosTabela resultado = consultaUsecase.consultarTabela();

        // Assert (Verificação)
        assertNotNull(resultado);

        // Verifica se o método do port foi chamado exatamente uma vez
        verify(consultaPort, times(1)).consultarTabela();
    }

}