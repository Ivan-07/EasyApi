package com.ivan.easyapiclientsdk;

import com.ivan.easyapiclientsdk.client.EasyApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivan
 * @create 2023/4/17 18:18
 */
@Configuration
@ConfigurationProperties("easyapi.client")
@Data
@ComponentScan
public class EasyApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public EasyApiClient easyApiClient() {
        return new EasyApiClient(accessKey, secretKey);
    }
}
