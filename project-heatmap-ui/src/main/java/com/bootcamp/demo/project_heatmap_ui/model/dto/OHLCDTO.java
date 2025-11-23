package com.bootcamp.demo.project_heatmap_ui.model.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OHLCDTO {
  private Long stockId;
  private String symbol;
  private LocalDate date;
  private Double open;
  private Double high;
  private Double low;
  private Double close;
  private Long volume;
}
