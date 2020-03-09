package com.panin.testtask.weatherservice.weatherproviders;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestExecutor {
    public ResponseEntity<String> executeRequest(String url) {
        return new RestTemplate().getForEntity(url, String.class);
    }
}
