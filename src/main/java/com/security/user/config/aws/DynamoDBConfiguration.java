package com.security.user.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Data
@Configuration
@Scope("prototype")
public class DynamoDBConfiguration {
    private final DynamoDBConfigDto config;
    public DynamoDBConfiguration(DynamoDBConfigDto config) {
        this.config = config;
    }
    @Bean
    public DynamoDBMapper amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(config.getEndpoint(),  config.getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(config.getAccessKey(), config.getSecret())))
                .build();
   return new DynamoDBMapper(amazonDynamoDB);
}
}