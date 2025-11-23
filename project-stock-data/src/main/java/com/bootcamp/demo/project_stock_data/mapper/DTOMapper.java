package com.bootcamp.demo.project_stock_data.mapper;

import org.springframework.stereotype.Component;
import com.bootcamp.demo.project_stock_data.dto.HeatDTO;
import com.bootcamp.demo.project_stock_data.dto.OHLCDTO;
import com.bootcamp.demo.project_stock_data.dto.ProfileDTO;
import com.bootcamp.demo.project_stock_data.entity.OHLCEntity;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;
import com.bootcamp.demo.project_stock_data.model.dto.QuoteDTO;

@Component
public class DTOMapper {
  public ProfileDTO map(ProfileEntity profileEntity) {
    return ProfileDTO.builder() //
        .stockId(profileEntity.getStockEntity().getId())
        .symbol(profileEntity.getSymbol())
        .shareOutstanding(profileEntity.getShareOutstanding())
        .marketCap(profileEntity.getMarketCap()).logo(profileEntity.getLogo())
        .exchange(profileEntity.getExchange())
        .industry(profileEntity.getIndustry())
        .ipoDate(profileEntity.getIpoDate()).market(profileEntity.getMarket())
        .name(profileEntity.getName())
        .id(profileEntity.getId())
        .webUrl(profileEntity.getWebUrl())
        .build();
  }

  public OHLCDTO map(OHLCEntity ohlcEntity) {
    return OHLCDTO.builder() //
        .stockId(ohlcEntity.getStockEntity().getId())
        .symbol(ohlcEntity.getStockEntity().getSymbol())
        .date(ohlcEntity.getDate()) //
        .open(ohlcEntity.getOpen()) //
        .high(ohlcEntity.getHigh()) //
        .low(ohlcEntity.getLow()) //
        .close(ohlcEntity.getClose()) //
        .volume(ohlcEntity.getVolume()) //
        .build();
  }

  public HeatDTO map(QuoteDTO dto, ProfileEntity entity) {
    return HeatDTO.builder() //
        .stockId(entity == null ? null : entity.getStockEntity().getId()) //
        .symbol(entity == null ? null : entity.getSymbol()) //
        .name(entity == null ? null : entity.getName())//
        .marketCap(entity == null ? null : entity.getMarketCap()) //
        .industry(entity == null ? null : entity.getIndustry()) //
        .ipoDate(entity == null ? null : entity.getIpoDate()) //
        .webUrl(entity == null ? null : entity.getWebUrl()) //
        .shareOutstanding(entity == null ? null : entity.getShareOutstanding()) //
        .logo(entity == null ? null : entity.getLogo()) //
        .price(dto == null ? null : dto.getMarketPrice()) //
        .marketPriceChg(dto == null ? null : dto.getMarketPriceChg()) //
        .marketPriceChgPct(dto == null ? null : dto.getMarketPriceChgPct()) //
        .build();
  }
}
