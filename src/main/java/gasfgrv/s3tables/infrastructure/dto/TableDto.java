package gasfgrv.s3tables.infrastructure.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record TableDto(
        String name,
        String type,
        String tableArn,
        List<String> namespace,
        String namespaceId,
        String versionToken,
        String metadataLocation,
        String warehouseLocation,
        ZonedDateTime createdAt,
        String createdBy,
        ZonedDateTime modifiedAt,
        String ownerAccountId,
        String format,
        String tableBucketId
) {
}
