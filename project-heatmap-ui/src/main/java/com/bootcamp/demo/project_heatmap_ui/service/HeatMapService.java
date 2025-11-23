package com.bootcamp.demo.project_heatmap_ui.service;

import java.util.List;
import com.bootcamp.demo.project_heatmap_ui.model.dto.HeatDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.OHLCDTO;
import com.bootcamp.demo.project_heatmap_ui.model.dto.ProfileDTO;

public interface HeatMapService {
  List<HeatDTO> getHeatMap();

  List<OHLCDTO> getOHLC(Long stockId);

  ProfileDTO getProfile(Long stockId);
}
