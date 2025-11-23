package com.bootcamp.demo.project_stock_data.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bootcamp.demo.project_stock_data.entity.OHLCEntity;
import com.bootcamp.demo.project_stock_data.entity.StockEntity;
import com.bootcamp.demo.project_stock_data.repository.OHLCRepository;
import com.bootcamp.demo.project_stock_data.repository.StockRepository;
import com.bootcamp.demo.project_stock_data.service.OHLCService;

@Service
public class OHLCServiceImpl implements OHLCService {
  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private OHLCRepository ohlcRepository;

  @Override
  public List<OHLCEntity> getOHLCs(Long stockId) {
    StockEntity stockEntity = this.stockRepository.findById(stockId)
        .orElseThrow(() -> new RuntimeException("Stock ID is invalid."));
    return this.ohlcRepository.findByStockEntity(stockEntity);
  }
}
