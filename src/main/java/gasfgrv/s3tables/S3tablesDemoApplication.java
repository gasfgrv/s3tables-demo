package gasfgrv.s3tables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class S3tablesDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(S3tablesDemoApplication.class, args);
    }

}
