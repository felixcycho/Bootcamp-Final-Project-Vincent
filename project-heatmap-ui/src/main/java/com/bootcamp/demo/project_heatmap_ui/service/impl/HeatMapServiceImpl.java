package com.bootcamp.demo.project_heatmap_ui.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.demo.project_heatmap_ui.model.dto.HeatDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.OHLCDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.ProfileDTO;
import com.bootcamp.demo.project_heatmap_ui.service.HeatMapService;

@Service
public class HeatMapServiceImpl implements HeatMapService {
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public List<HeatDTO> getHeatMap() {
    String url = UriComponentsBuilder.newInstance() //
        .scheme("http") //
        .host("stock-data-app:8072/heatmap/stock/realtime/data") //
        .build() //
        .toUriString();
    System.out.println("stock-data heatmap url=" + url);
    return Arrays.asList(this.restTemplate.getForObject(url, HeatDTO[].class));
  }

  @Override
  public List<OHLCDTO> getOHLC(Long stockId) {
    String url = UriComponentsBuilder.newInstance() //
        .scheme("http") //
        .host("stock-data-app:8072/heatmap/stock/history/candlesticks") //
        .queryParam("id", stockId) //
        .build() //
        .toUriString();
    System.out.println("stock-data ohlc url=" + url);
    return Arrays.asList(this.restTemplate.getForObject(url, OHLCDTO[].class));
  }

  @Override
  public ProfileDTO getProfile(Long stockId) {
    String url = UriComponentsBuilder.newInstance() //
        .scheme("http") //
        .host("stock-data-app:8072/heatmap/stock/profile") //
        .queryParam("id", stockId) //
        .build() //
        .toUriString();
    System.out.println("stock-data ohlc url=" + url);
    return this.restTemplate.getForObject(url, ProfileDTO.class);
  }
}
