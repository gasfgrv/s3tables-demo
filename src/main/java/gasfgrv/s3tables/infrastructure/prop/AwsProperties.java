package gasfgrv.s3tables.infrastructure.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsProperties(
        String accessKey,
        String secretKey,
        String sessionToken,
        String region,
        String s3TableArn
) {

}
