package com.bootcamp.demo.project_data_provider.dto.yahoofinance;

import java.time.LocalDateTime;
import com.bootcamp.demo.project_data_provider.lib.UnixToHKTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuoteDTO {
  private String symbol;
  private Double marketPrice;
  private Double marketPriceChg;
  private Double marketPriceChgPct;
  private Double marketPriceOpen;
  private Double marketPriceHigh;
  private Double marketPriceLow;
  private String marketPriceRange;
  private Double marketPrevClose;
  private Long marketVolume;
  private String marketState; // REGULAR,PRE,POST,CLOSED,AUCTION
  @JsonDeserialize(using = UnixToHKTimeDeserializer.class)
  private Long marketTime;
  @Builder.Default
  private LocalDateTime quoteTime = LocalDateTime.now();
}
