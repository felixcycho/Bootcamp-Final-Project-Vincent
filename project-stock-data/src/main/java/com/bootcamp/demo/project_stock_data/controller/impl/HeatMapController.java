package com.bootcamp.demo.project_stock_data.controller.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.demo.project_stock_data.controller.HeatMapOperation;
import com.bootcamp.demo.project_stock_data.dto.HeatDTO;
import com.bootcamp.demo.project_stock_data.dto.OHLCDTO;
import com.bootcamp.demo.project_stock_data.dto.ProfileDTO;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;
import com.bootcamp.demo.project_stock_data.entity.StockEntity;
import com.bootcamp.demo.project_stock_data.mapper.DTOMapper;
import com.bootcamp.demo.project_stock_data.model.dto.QuoteDTO;
import com.bootcamp.demo.project_stock_data.service.OHLCService;
import com.bootcamp.demo.project_stock_data.service.StockService;

@RestController
public class HeatMapController implements HeatMapOperation {
  @Autowired
  private StockService stockService;

  @Autowired
  private OHLCService ohlcService;

  @Autowired
  private DTOMapper dtoMapper;

  @Override
  public List<HeatDTO> getHeatMapData(String market) {
    // Get Active Stock (by market)
    List<StockEntity> stockEntities = this.stockService.getActiveStocks(market);

    // Call Yahoo Finance, all in once.
    List<String> activeSymbols = stockEntities.stream() //
        .map(e -> e.getSymbol()).collect(Collectors.toList());
    Map<String, QuoteDTO> quoteMap =
        this.stockService.getYFQuote(activeSymbols);
    System.out.println("Yahoo result length=" + quoteMap.size());

    // Call Database (or Redis) for profile data.
    Map<String, ProfileEntity> profileMap =
        stockService.findProfiles(stockEntities);
    System.out.println("Finnhub result length=" + profileMap.size());

    return stockEntities.stream() //
        .map(e -> this.dtoMapper.map(quoteMap.get(e.getSymbol()),
            profileMap.get(e.getSymbol()))) //
        .collect(Collectors.toList());
  }

  @Override
  public List<OHLCDTO> getCandlestick(Long stockId) {
    return this.ohlcService.getOHLCs(stockId).stream() //
        .map(e -> this.dtoMapper.map(e)) //
        .collect(Collectors.toList());
  }

  @Override
  public ProfileDTO getProfile(Long stockId) {
    ProfileEntity profileEntity = stockService.findProfile(stockId)
        .orElseThrow(() -> new RuntimeException("Invalid Stock ID."));
    return this.dtoMapper.map(profileEntity);
  }
}
