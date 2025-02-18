package ru.otus.java.pro.mt.core.transfers.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.otus.java.pro.mt.core.transfers.configs.properties.IntegrationProperties;

@Configuration
public class RestClientsConfig {
    // @Bean
    public RestTemplate commonRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestClient limitsClient(IntegrationProperties properties) {
        return RestClientFactory.getClient(properties.getLimits().getRestClientProps());
    }
}
