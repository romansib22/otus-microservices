package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestClientProps {
    private String url;
    private int readTimeout;
    private int connectionTimeout;
}
