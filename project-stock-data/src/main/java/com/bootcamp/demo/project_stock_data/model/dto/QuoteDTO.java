package com.bootcamp.demo.project_stock_data.model.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
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
  private Long marketTime; // TBC.
  private LocalDateTime quoteTime;
}
