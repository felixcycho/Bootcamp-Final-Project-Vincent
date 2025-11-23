package com.bootcamp.demo.project_heatmap_ui.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OHLCPageDTO {
  private Long stockId;
  private String symbol;
  private String companyName;
  private Double marketCap;
  private String industry;
  private Double shareOutstanding;
  private String logo;
  private List<OHLC> ohlcs;

  @Getter
  @Builder
  public static class OHLC {
    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
  }
}
