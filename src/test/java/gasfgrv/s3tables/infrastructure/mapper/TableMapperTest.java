package gasfgrv.s3tables.infrastructure.mapper;

import gasfgrv.s3tables.infrastructure.dto.TableDto;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3tables.model.GetTableResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TableMapperTest {

    @Test
    void mapsAllFieldsCorrectly_whenAllFieldsArePresent() {
        Instant createdAt = Instant.parse("2024-06-01T10:15:30.00Z");
        Instant modifiedAt = Instant.parse("2024-06-02T12:00:00.00Z");
        GetTableResponse response = GetTableResponse.builder()
                .name("table1")
                .type("ICEBERG")
                .tableARN("arn:aws:s3tables:region:account:table/table1")
                .namespace("namespace1")
                .namespaceId("ns-123")
                .versionToken("v1")
                .metadataLocation("s3://bucket/meta")
                .warehouseLocation("s3://bucket/warehouse")
                .createdAt(createdAt)
                .createdBy("creator")
                .modifiedAt(modifiedAt)
                .ownerAccountId("owner-acc")
                .format("PARQUET")
                .tableBucketId("bucket-123")
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertThat(dto.name()).isEqualTo("table1");
        assertThat(dto.type()).isEqualTo("ICEBERG");
        assertThat(dto.tableArn()).isEqualTo("arn:aws:s3tables:region:account:table/table1");
        assertThat(dto.namespace()).contains("namespace1");
        assertThat(dto.namespaceId()).isEqualTo("ns-123");
        assertThat(dto.versionToken()).isEqualTo("v1");
        assertThat(dto.metadataLocation()).isEqualTo("s3://bucket/meta");
        assertThat(dto.warehouseLocation()).isEqualTo("s3://bucket/warehouse");
        assertThat(dto.createdAt()).isEqualTo(ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault()));
        assertThat(dto.createdBy()).isEqualTo("creator");
        assertThat(dto.modifiedAt()).isEqualTo(ZonedDateTime.ofInstant(modifiedAt, ZoneId.systemDefault()));
        assertThat(dto.ownerAccountId()).isEqualTo("owner-acc");
        assertThat(dto.format()).isEqualTo("PARQUET");
        assertThat(dto.tableBucketId()).isEqualTo("bucket-123");
    }

    @Test
    void returnsNullForZonedDateTimeFields_whenInstantsAreNull() {
        GetTableResponse response = GetTableResponse.builder()
                .createdAt(null)
                .modifiedAt(null)
                .build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertThat(dto.createdAt()).isNull();
        assertThat(dto.modifiedAt()).isNull();
    }

    @Test
    void mapsNullFields_whenOptionalFieldsAreMissing() {
        GetTableResponse response = GetTableResponse.builder().build();

        TableDto dto = new TableMapperImpl().toDto(response);

        assertThat(dto.name()).isNull();
        assertThat(dto.type()).isNull();
        assertThat(dto.tableArn()).isNull();
        assertThat(dto.namespace()).isEmpty();
        assertThat(dto.namespaceId()).isNull();
        assertThat(dto.versionToken()).isNull();
        assertThat(dto.metadataLocation()).isNull();
        assertThat(dto.warehouseLocation()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.createdBy()).isNull();
        assertThat(dto.modifiedAt()).isNull();
        assertThat(dto.ownerAccountId()).isNull();
        assertThat(dto.format()).isNull();
        assertThat(dto.tableBucketId()).isNull();
    }

}