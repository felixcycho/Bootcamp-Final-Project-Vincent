package com.bootcamp.demo.project_data_provider.mapper;

import org.springframework.stereotype.Component;
import com.bootcamp.demo.project_data_provider.dto.finnhub.ProfileDTO;
import com.bootcamp.demo.project_data_provider.dto.yahoofinance.QuoteDTO;
import com.bootcamp.demo.project_data_provider.finnhub.dto.FinProfileDTO;
import com.bootcamp.demo.project_data_provider.model.YahooQuoteDTO;

@Component
public class DTOMapper {
  public ProfileDTO map(FinProfileDTO profileDto, String symbol) {
    return ProfileDTO.builder() //
        .symbol(symbol)
        .country(profileDto.getCountry()) //
        .currency(profileDto.getCurrency()) //
        .exchange(profileDto.getExchange()) //
        .estimateCurrency(profileDto.getEstimateCurrency()) //
        .finnhubIndustry(profileDto.getFinnhubIndustry()) //
        .ipo(profileDto.getIpo()) //
        .logo(profileDto.getLogo()) //
        .marketCapitalization(profileDto.getMarketCapitalization()) //
        .name(profileDto.getName()) //
        .phone(profileDto.getPhone()) //
        .shareOutstanding(profileDto.getShareOutstanding()) //
        .ticker(profileDto.getTicker()) //
        .weburl(profileDto.getWeburl()) //
        .build();
  }

  public QuoteDTO map(YahooQuoteDTO.QuoteBody.QuoteResult yahooQuote) {
    return QuoteDTO.builder() //
        .symbol(yahooQuote.getSymbol()) //
        .marketState(yahooQuote.getMarketState()) //
        .marketPriceChg(yahooQuote.getRegularMarketChange()) //
        .marketPriceChgPct(yahooQuote.getRegularMarketChangePercent()) //
        .marketPrice(yahooQuote.getRegularMarketPrice()) //
        .marketPriceOpen(yahooQuote.getRegularMarketOpen()) //
        .marketPriceHigh(yahooQuote.getRegularMarketDayHigh()) //
        .marketPriceLow(yahooQuote.getRegularMarketDayLow()) //
        .marketVolume(yahooQuote.getRegularMarketVolume()) //
        .marketPrevClose(yahooQuote.getRegularMarketPreviousClose()) //
        .marketPriceRange(yahooQuote.getRegularMarketDayRange()) //
        .marketTime(yahooQuote.getRegularMarketTime()) //
        .build();
  }
}
