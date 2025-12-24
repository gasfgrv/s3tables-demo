package gasfgrv.s3tables.controller.dto;

import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.infrastructure.mapper.TableMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3tables.model.GetTableResponse;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableDtoTest {

    @Test
    @DisplayName("Converte resposta completa para TableDto corretamente")
    void converteRespostaCompletaParaTableDtoCorretamente() {
        GetTableResponse response = GetTableResponse.builder()
                .name("table1")
                .type("ICEBERG")
                .tableARN("arn:aws:s3:::table1")
                .namespace("ns1", "ns2")
                .namespaceId("123")
                .versionToken("token")
                .metadataLocation("meta")
                .warehouseLocation("wh")
                .createdAt(ZonedDateTime.parse("2024-06-01T10:15:30-03:00[America/Sao_Paulo]").toInstant())
                .createdBy("user")
                .modifiedAt(ZonedDateTime.parse("2024-06-02T11:15:30-03:00[America/Sao_Paulo]").toInstant())
                .ownerAccountId("acc")
                .format("parquet")
                .tableBucketId("bucket1")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertEquals("table1", dto.name());
        assertEquals("ICEBERG", dto.type());
        assertEquals("arn:aws:s3:::table1", dto.tableArn());
        assertEquals(List.of("ns1", "ns2"), dto.namespace());
        assertEquals("123", dto.namespaceId());
        assertEquals("token", dto.versionToken());
        assertEquals("meta", dto.metadataLocation());
        assertEquals("wh", dto.warehouseLocation());
        assertEquals(ZonedDateTime.parse("2024-06-01T10:15:30-03:00[America/Sao_Paulo]"), dto.createdAt());
        assertEquals("user", dto.createdBy());
        assertEquals(ZonedDateTime.parse("2024-06-02T11:15:30-03:00[America/Sao_Paulo]"), dto.modifiedAt());
        assertEquals("acc", dto.ownerAccountId());
        assertEquals("parquet", dto.format());
        assertEquals("bucket1", dto.tableBucketId());
    }

    @Test
    @DisplayName("Retorna lista vazia quando namespace está ausente")
    void retornaListaVaziaQuandoNamespaceEstaAusente() {
        GetTableResponse response = GetTableResponse.builder()
                .name("table2")
                .type("ICEBERG")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertTrue(dto.namespace().isEmpty());
    }

    @Test
    @DisplayName("Retorna null para datas ausentes")
    void retornaNullParaDatasAusentes() {
        GetTableResponse response = GetTableResponse.builder()
                .name("table3")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertNull(dto.createdAt());
        assertNull(dto.modifiedAt());
    }

    @Test
    @DisplayName("Converte namespace com um único elemento corretamente")
    void converteNamespaceComUmUnicoElementoCorretamente() {
        GetTableResponse response = GetTableResponse.builder()
                .namespace("ns1")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertEquals(List.of("ns1"), dto.namespace());
    }

    @Test
    @DisplayName("Ignora espaços extras e parênteses na resposta")
    void ignoraEspacosExtrasEParentesesNaResposta() {
        GetTableResponse response = GetTableResponse.builder()
                .name("table4")
                .type("ICEBERG")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertEquals("table4", dto.name());
        assertEquals("ICEBERG", dto.type());
    }

    @Test
    @DisplayName("Retorna null para campos não presentes na resposta")
    void retornaNullParaCamposNaoPresentesNaResposta() {
        GetTableResponse response = GetTableResponse.builder()
                .name("table5")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertNull(dto.type());
        assertNull(dto.tableArn());
        assertNull(dto.createdAt());
        assertNull(dto.tableBucketId());
    }
}