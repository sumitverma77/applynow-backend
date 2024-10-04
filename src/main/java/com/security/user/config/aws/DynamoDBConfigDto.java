package com.security.user.config.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Data
@Configuration
@Scope("prototype")
@ConfigurationProperties(prefix = "aws.dynamodb")
public class DynamoDBConfigDto {
    private String accessKey;
    private String secret;
    private String region;
    private String endpoint;
}
