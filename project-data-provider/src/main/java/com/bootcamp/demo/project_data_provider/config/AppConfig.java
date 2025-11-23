package com.bootcamp.demo.project_data_provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bootcamp.demo.project_data_provider.finnhub.client.FinnhubClient;

@Configuration
public class AppConfig {
  @Bean
  FinnhubClient finnhubClient() {
    return new FinnhubClient();
  }
}