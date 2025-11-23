package com.bootcamp.demo.project_heatmap_ui.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.demo.project_heatmap_ui.dto.OHLCPageDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.HeatDTO;

public interface HeatMapDataOperation {
  @GetMapping(value = "/data/heatmap")
  List<HeatDTO> getHeatMap();
  
  @GetMapping(value = "/data/ohlc")
  OHLCPageDTO getOHLCPage(@RequestParam(value = "id") Long stockId);
}
