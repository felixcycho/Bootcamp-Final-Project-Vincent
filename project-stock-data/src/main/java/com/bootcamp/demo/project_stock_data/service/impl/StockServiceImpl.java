package com.bootcamp.demo.project_stock_data.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;
import com.bootcamp.demo.project_stock_data.entity.StockEntity;
import com.bootcamp.demo.project_stock_data.model.dto.ProfileDTO;
import com.bootcamp.demo.project_stock_data.model.dto.QuoteDTO;
import com.bootcamp.demo.project_stock_data.repository.ProfileRepository;
import com.bootcamp.demo.project_stock_data.repository.StockRepository;
import com.bootcamp.demo.project_stock_data.service.StockService;


@Service
public class StockServiceImpl implements StockService {
  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Optional<ProfileEntity> findProfile(Long stockId) {
    StockEntity stockEntity = this.stockRepository.findById(stockId)
        .orElseThrow(() -> new RuntimeException("Invalid Stock ID."));
    return this.profileRepository.findByStockEntity(stockEntity);
  }

  @Override
  public ProfileDTO getFinnhubProfile(String symbol) {
    String url = UriComponentsBuilder.newInstance() //
        .scheme("http") //
        .host("data-provider-app:8071") //
        .pathSegment("data/stock/finnhub") //
        .path("/profile") //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    System.out.println("finnhub url=" + url);
    return this.restTemplate.getForObject(url, ProfileDTO.class);
  }

  @Override
  public Map<String, QuoteDTO> getYFQuote(List<String> symbols) {
    String symbolsWithComma = String.join(",", symbols);
    String url = UriComponentsBuilder.newInstance() //
        .scheme("http") //
        .host("data-provider-app:8071/data/stock/yahoofinance/quote") //
        .queryParam("symbols", symbolsWithComma) //
        .build() //
        .toUriString();
    System.out.println("yahoo url=" + url);

    List<QuoteDTO> quoteDTOs =
        Arrays.asList(this.restTemplate.getForObject(url, QuoteDTO[].class));
    return quoteDTOs.stream() //
        .collect(Collectors.toMap(e -> e.getSymbol(), e -> e));
  }

  @Override
  public List<StockEntity> getActiveStocks(String market) {
    if (market == null)
      throw new IllegalArgumentException("Market cannot be null.");
    return this.stockRepository.findAll().stream() //
        .filter(e -> "Y".equals(e.getIsActive()) //
            && market.equals(e.getMarket())) //
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, ProfileEntity> findProfiles(List<StockEntity> stockEntities) {
    return this.profileRepository.findByStockEntityIn(stockEntities).stream()
        .collect(Collectors.toMap(e -> e.getSymbol(), e -> e));
  }

  @Override
  public ProfileEntity saveProfile(ProfileEntity profileEntities) {
    return this.profileRepository.save(profileEntities);
  }

  @Override
  public void deleteAllProfiles() {
    this.profileRepository.deleteAll();
  }
}
