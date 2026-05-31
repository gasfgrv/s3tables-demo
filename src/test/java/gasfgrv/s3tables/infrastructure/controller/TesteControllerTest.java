package gasfgrv.s3tables.infrastructure.controller;

import gasfgrv.s3tables.application.ConsultaUsecase;
import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import gasfgrv.s3tables.mocks.GenerateMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TesteController.class)
class TesteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsultaUsecase consultaUsecase;

    @MockitoBean
    private TableMapper tableMapper;

    @Test
    @DisplayName("Deve retornar status 200 e os dados da tabela ao chamar o endpoint de teste")
    void deveRetornarDadosTabelaComSucesso() throws Exception {
        // Arrange
        DadosTabela dadosDominio = GenerateMockData.generateDomain();
        TableDto dtoResposta = GenerateMockData.generateDto();

        when(consultaUsecase.consultarTabela()).thenReturn(dadosDominio);
        when(tableMapper.toDto(dadosDominio)).thenReturn(dtoResposta);

        // Act & Assert
        mockMvc.perform(get("/teste").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.format").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

}