package com.bootcamp.demo.project_stock_data.mapper;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import com.bootcamp.demo.project_stock_data.model.dto.ProfileDTO;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;

@Component
public class EntityMapper {
  public ProfileEntity map(ProfileDTO dto) {
    LocalDate ipoDate =
        dto.getIpo() != null ? LocalDate.parse(dto.getIpo()) : null;
    return ProfileEntity.builder() //
        .symbol(dto.getSymbol()) //
        .exchange(dto.getExchange()) //
        .logo(dto.getLogo()) //
        .ipoDate(ipoDate) //
        .webUrl(dto.getWeburl()) //
        .name(dto.getName()) //
        .industry(dto.getFinnhubIndustry()) //
        .market("US") //
        .shareOutstanding(dto.getShareOutstanding()) //
        .marketCap(dto.getMarketCapitalization()) //
        .build();
  }
}
