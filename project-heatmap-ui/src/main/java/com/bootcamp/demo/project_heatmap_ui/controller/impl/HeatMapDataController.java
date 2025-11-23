package com.bootcamp.demo.project_heatmap_ui.controller.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.demo.project_heatmap_ui.controller.HeatMapDataOperation;
import com.bootcamp.demo.project_heatmap_ui.dto.OHLCPageDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.HeatDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.OHLCDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.ProfileDTO;
import com.bootcamp.demo.project_heatmap_ui.service.HeatMapService;

@RestController
public class HeatMapDataController implements HeatMapDataOperation {
  @Autowired
  private HeatMapService heatMapService;

  @Override
  public List<HeatDTO> getHeatMap() {
    return this.heatMapService.getHeatMap();
  }

  @Override
  public OHLCPageDTO getOHLCPage(Long stockId) {
    List<OHLCDTO> ohlcdtos = this.heatMapService.getOHLC(stockId);
    ProfileDTO profileDTO = this.heatMapService.getProfile(stockId);
    List<OHLCPageDTO.OHLC> ohlcs = ohlcdtos.stream() //
        .map(e -> {
          return OHLCPageDTO.OHLC.builder() //
              .open(e.getOpen()) //
              .close(e.getClose()) //
              .high(e.getHigh()) //
              .low(e.getLow()) //
              .volume(e.getVolume()) //
              .date(e.getDate()) //
              .build();
        }).collect(Collectors.toList());
    return OHLCPageDTO.builder() //
        .stockId(profileDTO.getStockId())
        .companyName(profileDTO.getName())
        .symbol(profileDTO.getSymbol())
        .marketCap(profileDTO.getMarketCap())
        .industry(profileDTO.getIndustry())
        .shareOutstanding(profileDTO.getShareOutstanding())
        .logo(profileDTO.getLogo())
        .ohlcs(ohlcs) //
        .build();
  }
}
