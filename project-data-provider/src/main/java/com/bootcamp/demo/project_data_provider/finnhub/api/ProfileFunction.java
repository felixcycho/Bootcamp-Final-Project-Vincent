package com.bootcamp.demo.project_data_provider.finnhub.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.demo.project_data_provider.finnhub.dto.FinProfileDTO;
import com.bootcamp.demo.project_data_provider.finnhub.exception.FinnhubError;
import com.bootcamp.demo.project_data_provider.finnhub.util.Finnhub;
import com.bootcamp.demo.project_data_provider.lib.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ProfileFunction {
  RestTemplate getRestTemplate();

  // https://finnhub.io/api/v1/stock/profile2?symbol=TSLA&token=d1n3u8hr01qlvnp5dhv0d1n3u8hr01qlvnp5dhvg
  default FinProfileDTO getProfile(String symbol, String apiKey) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("symbol", List.of(symbol));

    System.out.println("apikey=" + apiKey);
    params.put("token", List.of(apiKey));

    String url = UriComponentsBuilder.newInstance() //
        .scheme("https") //
        .host(Finnhub.API_HOST) //
        .pathSegment(Finnhub.VERSION_QUOTE) //
        .path(Finnhub.ENDPOINT_QUOTE) //
        .queryParams(params) //
        .build() //
        .toUriString();

    System.out.println("url=" + url);
    ResponseEntity<String> response =
        this.getRestTemplate().getForEntity(url, String.class);
    try {
      if (!response.getStatusCode().equals(HttpStatus.OK)) {
        throw new BusinessException(FinnhubError.PROFILE2_EX);
      }
      return new ObjectMapper().readValue(response.getBody(),
          FinProfileDTO.class);
    } catch (JsonProcessingException ex) {
      throw new BusinessException(FinnhubError.JSON_PROCESSING_EX);
    }
  }
}
