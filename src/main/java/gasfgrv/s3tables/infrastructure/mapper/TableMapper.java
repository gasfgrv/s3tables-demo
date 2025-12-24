package gasfgrv.s3tables.infrastructure.mapper;

import gasfgrv.s3tables.infrastructure.dto.TableDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import software.amazon.awssdk.services.s3tables.model.GetTableResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface TableMapper {

    @Mappings({
            @Mapping(expression = "java(tableResponse.name())", target = "name"),
            @Mapping(expression = "java(tableResponse.typeAsString())", target = "type"),
            @Mapping(expression = "java(tableResponse.tableARN())", target = "tableArn"),
            @Mapping(expression = "java(tableResponse.namespace())", target = "namespace"),
            @Mapping(expression = "java(tableResponse.namespaceId())", target = "namespaceId"),
            @Mapping(expression = "java(tableResponse.versionToken())", target = "versionToken"),
            @Mapping(expression = "java(tableResponse.metadataLocation())", target = "metadataLocation"),
            @Mapping(expression = "java(tableResponse.warehouseLocation())", target = "warehouseLocation"),
            @Mapping(expression = "java(instantToZonedDateTime(tableResponse.createdAt()))", target = "createdAt"),
            @Mapping(expression = "java(tableResponse.createdBy())", target = "createdBy"),
            @Mapping(expression = "java(instantToZonedDateTime(tableResponse.modifiedAt()))", target = "modifiedAt"),
            @Mapping(expression = "java(tableResponse.ownerAccountId())", target = "ownerAccountId"),
            @Mapping(expression = "java(tableResponse.formatAsString())", target = "format"),
            @Mapping(expression = "java(tableResponse.tableBucketId())", target = "tableBucketId")
    })
    TableDto toDto(GetTableResponse tableResponse);

    @Named("instantToZonedDateTime")
    default ZonedDateTime instantToZonedDateTime(Instant instant) {
        return instant == null ? null
                : ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
