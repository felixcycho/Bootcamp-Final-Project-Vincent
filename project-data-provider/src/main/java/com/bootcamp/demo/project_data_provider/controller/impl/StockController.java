package com.bootcamp.demo.project_data_provider.controller.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.demo.project_data_provider.controller.StockOperation;
import com.bootcamp.demo.project_data_provider.dto.finnhub.ProfileDTO;
import com.bootcamp.demo.project_data_provider.dto.yahoofinance.QuoteDTO;
import com.bootcamp.demo.project_data_provider.finnhub.dto.FinProfileDTO;
import com.bootcamp.demo.project_data_provider.mapper.DTOMapper;
import com.bootcamp.demo.project_data_provider.service.StockDataService;
import com.bootcamp.demo.project_data_provider.service.StockQuoteService;

@RestController
public class StockController implements StockOperation {
  @Autowired
  @Qualifier(value = "yahooQuoteService")
  private StockQuoteService stockQuoteService;

  @Autowired
  @Qualifier(value = "finnhubDataService")
  private StockDataService stockDataService;

  @Autowired
  private DTOMapper dtoMapper;

  @Override
  public List<QuoteDTO> getQuote(List<String> symbols) {
     List<QuoteDTO> quoteDTOs = this.stockQuoteService.getQuotes(symbols).stream() //
        .map(e -> this.dtoMapper.map(e)) //
        .collect(Collectors.toList());
    return quoteDTOs;
  }

  @Override
  public ProfileDTO getFinnhub(String symbol) {
    FinProfileDTO finProfileDTO = this.stockDataService.getProfileData(symbol);
    return this.dtoMapper.map(finProfileDTO, symbol);
  }
}
