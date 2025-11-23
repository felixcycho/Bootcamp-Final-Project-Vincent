package com.bootcamp.demo.project_data_provider.service;

import java.util.List;
import com.bootcamp.demo.project_data_provider.model.YahooQuoteDTO;

public interface StockQuoteService {

  List<YahooQuoteDTO.QuoteBody.QuoteResult> getQuotes(List<String> symbols);
  
}
