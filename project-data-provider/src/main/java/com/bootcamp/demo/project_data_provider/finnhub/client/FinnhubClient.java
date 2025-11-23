package com.bootcamp.demo.project_data_provider.finnhub.client;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import com.bootcamp.demo.project_data_provider.finnhub.api.ProfileFunction;

public class FinnhubClient implements ProfileFunction {
  private RestTemplate restTemplate;

  public FinnhubClient() {
    // Set Connection and Read Timeout.
    this.restTemplate = new RestTemplateBuilder() //
        .connectTimeout(Duration.ofSeconds(5L)) //
        .readTimeout(Duration.ofSeconds(5L)) //
        .build();
  }

  @Override
  public RestTemplate getRestTemplate() {
    return this.restTemplate;
  }
}
