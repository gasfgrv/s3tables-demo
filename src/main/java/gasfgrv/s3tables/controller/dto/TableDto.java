package gasfgrv.s3tables.controller.dto;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String TableBucketId
) {

    public static TableDto toDto(String tableResponse) {
        String conteudo = tableResponse.trim()
                .replaceFirst("^GetTableResponse\\(", "")
                .replaceAll("\\)$", "");

        Map<String, String> mapa = new HashMap<>();

        Pattern padrao = Pattern.compile("(\\w+)=((\\[.*?])|([^,]+))(,\\s*)?");
        Matcher matcher = padrao.matcher(conteudo);

        while (matcher.find()) {
            String chave = matcher.group(1);
            String valor = matcher.group(2).trim();
            mapa.put(chave, valor);
        }

        return new TableDto(
                mapa.get("name"),
                mapa.get("type"),
                mapa.get("tableArn"),
                converterParaLista(mapa.get("namespace")),
                mapa.get("namespaceId"),
                mapa.get("versionToken"),
                mapa.get("metadataLocation"),
                mapa.get("warehouseLocation"),
                converterParaZonedDateTime(mapa.get("createdAt")),
                mapa.get("createdBy"),
                converterParaZonedDateTime(mapa.get("modifiedAt")),
                mapa.get("ownerAccountId"),
                mapa.get("format"),
                mapa.get("TableBucketId")
        );
    }

    private static List<String> converterParaLista(String namespace) {
        if (namespace == null || namespace.isEmpty()) {
            return Collections.emptyList();
        }

        namespace = namespace.replaceAll("^\\[|]$", "");
        String[] elementos = namespace.split(",\\s*");
        return List.of(elementos);
    }

    private static ZonedDateTime converterParaZonedDateTime(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        return ZonedDateTime.parse(data);
    }

}
