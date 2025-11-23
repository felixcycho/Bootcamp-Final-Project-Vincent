package com.bootcamp.demo.project_stock_data.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDTO {
  private Long id;
  private Long stockId;
  private String symbol;
  private String market; // US, HK
  private String exchange;
  private String industry;
  private LocalDate ipoDate;
  private String name;
  private String webUrl;
  private String logo;
  private Double shareOutstanding;
  private Double marketCap;
}
