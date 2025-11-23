package com.bootcamp.demo.project_data_provider.service.impl;

import java.io.InputStream;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.bootcamp.demo.project_data_provider.model.YahooQuoteDTO;
import com.bootcamp.demo.project_data_provider.service.StockQuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(value = "yahooQuoteService")
public class YahooQuoteServiceImpl implements StockQuoteService {
  @Override
  public List<YahooQuoteDTO.QuoteBody.QuoteResult> getQuotes(
      List<String> symbols) {
    // Yahoo Data hardcoded.
    try {
      ObjectMapper mapper = new ObjectMapper();
      InputStream inputStream =
          new ClassPathResource("sample_yahoo_snp500.json").getInputStream();
      return mapper.readValue(inputStream, YahooQuoteDTO.class).getBody()
          .getResults();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load JSON", e);
    }
  }
}
