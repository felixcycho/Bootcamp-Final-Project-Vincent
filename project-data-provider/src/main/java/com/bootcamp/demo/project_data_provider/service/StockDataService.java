package com.bootcamp.demo.project_data_provider.service;

import com.bootcamp.demo.project_data_provider.finnhub.dto.FinProfileDTO;

public interface StockDataService {
  FinProfileDTO getProfileData(String symbol);
}
