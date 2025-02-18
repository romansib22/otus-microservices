package ru.otus.java.pro.mt.core.transfers.configs;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProps;

@Component
public class RestClientFactory {
    public static RestClient getClient(RestClientProps properties) {
        return RestClient.builder()
                .requestFactory(getClientHttpRequestFactory(properties))
                .baseUrl(properties.getUrl())
                .build();
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory(RestClientProps properties) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(properties.getConnectionTimeout());
        clientHttpRequestFactory.setReadTimeout(properties.getReadTimeout());
        return clientHttpRequestFactory;
    }
}
