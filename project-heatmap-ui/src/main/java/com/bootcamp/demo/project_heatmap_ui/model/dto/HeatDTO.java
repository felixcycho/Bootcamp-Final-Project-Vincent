package com.bootcamp.demo.project_heatmap_ui.model.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class HeatDTO {
  private Long stockId;
  private String symbol;
  private String name;
  private Double price;
  private Double marketPriceChg;
  private Double marketPriceChgPct;
  private Double marketCap;
  private String industry;
  private LocalDate ipoDate;
  private String webUrl;
  private Double shareOutstanding;
  private String logo;
}
