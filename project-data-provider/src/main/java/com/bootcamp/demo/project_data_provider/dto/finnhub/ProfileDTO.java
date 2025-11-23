package com.bootcamp.demo.project_data_provider.dto.finnhub;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDTO {
  private String symbol;
  private String country;
  private String currency;
  private String estimateCurrency;
  private String exchange;
  private String finnhubIndustry;
  private String ipo;
  private String logo;
  private Double marketCapitalization;
  private String name;
  private String phone;
  private Double shareOutstanding;
  private String ticker;
  private String weburl;
  @Builder.Default
  private LocalDateTime quoteTime = LocalDateTime.now();
}
