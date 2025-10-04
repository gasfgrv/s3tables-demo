package gasfgrv.s3tables.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3tables.S3TablesClient;


@Configuration
public class AwsConfig {

    private final AwsProperties awsProperties;

    public AwsConfig(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public AwsCredentials awsCredentials() {
        if (awsProperties.getSessionToken() != null && !awsProperties.getSessionToken().isEmpty())
            return AwsSessionCredentials.create(awsProperties.getAccessKey(),
                    awsProperties.getSecretKey(),
                    awsProperties.getSessionToken());

        return AwsBasicCredentials.create(awsProperties.getAccessKey(), awsProperties.getSecretKey());
    }

    @Bean
    public S3TablesClient s3TablesClient(AwsCredentials awsCredentials) {
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);

        return S3TablesClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();
    }

}
