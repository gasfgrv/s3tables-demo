package gasfgrv.s3tables.controller.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableDtoTest {

    @Test
    @DisplayName("Converte resposta completa para TableDto corretamente")
    void converteRespostaCompletaParaTableDtoCorretamente() {
        String response = "GetTableResponse(" +
                "name=table1, " +
                "type=ICEBERG, " +
                "tableArn=arn:aws:s3:::table1, " +
                "namespace=[ns1, ns2], " +
                "namespaceId=123, " +
                "versionToken=token, " +
                "metadataLocation=meta, " +
                "warehouseLocation=wh, " +
                "createdAt=2024-06-01T10:15:30+01:00[Europe/London], " +
                "createdBy=user, modifiedAt=2024-06-02T11:15:30+01:00[Europe/London], " +
                "ownerAccountId=acc, " +
                "format=parquet, " +
                "TableBucketId=bucket1)";
        TableDto dto = TableDto.toDto(response);

        assertEquals("table1", dto.name());
        assertEquals("ICEBERG", dto.type());
        assertEquals("arn:aws:s3:::table1", dto.tableArn());
        assertEquals(List.of("ns1", "ns2"), dto.namespace());
        assertEquals("123", dto.namespaceId());
        assertEquals("token", dto.versionToken());
        assertEquals("meta", dto.metadataLocation());
        assertEquals("wh", dto.warehouseLocation());
        assertEquals(ZonedDateTime.parse("2024-06-01T10:15:30+01:00[Europe/London]"), dto.createdAt());
        assertEquals("user", dto.createdBy());
        assertEquals(ZonedDateTime.parse("2024-06-02T11:15:30+01:00[Europe/London]"), dto.modifiedAt());
        assertEquals("acc", dto.ownerAccountId());
        assertEquals("parquet", dto.format());
        assertEquals("bucket1", dto.TableBucketId());
    }

    @Test
    @DisplayName("Retorna lista vazia quando namespace está ausente")
    void retornaListaVaziaQuandoNamespaceEstaAusente() {
        String response = "GetTableResponse(name=table2, namespace=, type=ICEBERG)";
        TableDto dto = TableDto.toDto(response);

        assertTrue(dto.namespace().isEmpty());
    }

    @Test
    @DisplayName("Retorna null para datas ausentes")
    void retornaNullParaDatasAusentes() {
        String response = "GetTableResponse(name=table3, createdAt=, modifiedAt=)";
        TableDto dto = TableDto.toDto(response);

        assertNull(dto.createdAt());
        assertNull(dto.modifiedAt());
    }

    @Test
    @DisplayName("Converte namespace com um único elemento corretamente")
    void converteNamespaceComUmUnicoElementoCorretamente() {
        String response = "GetTableResponse(namespace=[ns1])";
        TableDto dto = TableDto.toDto(response);

        assertEquals(List.of("ns1"), dto.namespace());
    }

    @Test
    @DisplayName("Ignora espaços extras e parênteses na resposta")
    void ignoraEspacosExtrasEParentesesNaResposta() {
        String response = "  GetTableResponse(name=table4, type=ICEBERG)  ";
        TableDto dto = TableDto.toDto(response);

        assertEquals("table4", dto.name());
        assertEquals("ICEBERG", dto.type());
    }

    @Test
    @DisplayName("Retorna null para campos não presentes na resposta")
    void retornaNullParaCamposNaoPresentesNaResposta() {
        String response = "GetTableResponse(name=table5)";
        TableDto dto = TableDto.toDto(response);

        assertNull(dto.type());
        assertNull(dto.tableArn());
        assertNull(dto.createdAt());
        assertNull(dto.TableBucketId());
    }
}