package com.bootcamp.demo.project_stock_data.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.demo.project_stock_data.dto.HeatDTO;
import com.bootcamp.demo.project_stock_data.dto.OHLCDTO;
import com.bootcamp.demo.project_stock_data.dto.ProfileDTO;

public interface HeatMapOperation {
  @GetMapping(value = "/heatmap/stock/realtime/data")
  List<HeatDTO> getHeatMapData(@RequestParam(defaultValue = "US") String market);

  @GetMapping(value = "/heatmap/stock/history/candlesticks")
  List<OHLCDTO> getCandlestick(@RequestParam(value = "id") Long stockId);

  @GetMapping(value = "/heatmap/stock/profile")
  ProfileDTO getProfile(@RequestParam(value = "id") Long stockId);
  
}
