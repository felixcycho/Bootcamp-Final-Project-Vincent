package com.bootcamp.demo.project_data_provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bootcamp.demo.project_data_provider.finnhub.client.FinnhubClient;
import com.bootcamp.demo.project_data_provider.finnhub.dto.FinProfileDTO;
import com.bootcamp.demo.project_data_provider.service.StockDataService;

@Service (value = "finnhubDataService")
public class FinnhubDataServiceImpl implements StockDataService {
  @Value("${service-api.finnhub.api-key}")
  private String apiKey;

  @Autowired
  private FinnhubClient finnhubClient;

  @Override
  public FinProfileDTO getProfileData(String symbol) {
    return this.finnhubClient.getProfile(symbol, apiKey);
  }
}
