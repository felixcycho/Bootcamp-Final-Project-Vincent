package com.bootcamp.demo.project_data_provider.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.demo.project_data_provider.dto.finnhub.ProfileDTO;
import com.bootcamp.demo.project_data_provider.dto.yahoofinance.QuoteDTO;

public interface StockOperation {
  @GetMapping(value = "/data/stock/yahoofinance/quote")
  List<QuoteDTO> getQuote(@RequestParam List<String> symbols);

  @GetMapping(value = "/data/stock/finnhub/profile")
  ProfileDTO getFinnhub(@RequestParam String symbol);
}
