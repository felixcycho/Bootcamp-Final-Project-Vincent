package com.bootcamp.demo.project_stock_data.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;
import com.bootcamp.demo.project_stock_data.entity.StockEntity;
import com.bootcamp.demo.project_stock_data.model.dto.ProfileDTO;
import com.bootcamp.demo.project_stock_data.model.dto.QuoteDTO;

public interface StockService {
  // get finnhub profile from project-data-provider
  ProfileDTO getFinnhubProfile(String symbol);

  // get yahoo quote from project-data-provider
  Map<String, QuoteDTO> getYFQuote(List<String> symbols);

  // get Stock id
  List<StockEntity> getActiveStocks(String market);

  // get logo from redis + DB
  Map<String, ProfileEntity> findProfiles(List<StockEntity> stockEntities);

  // save Profiles in DB
  ProfileEntity saveProfile(ProfileEntity profileEntity);

  // delete all
  void deleteAllProfiles();

  Optional<ProfileEntity> findProfile(Long stockId);
}
