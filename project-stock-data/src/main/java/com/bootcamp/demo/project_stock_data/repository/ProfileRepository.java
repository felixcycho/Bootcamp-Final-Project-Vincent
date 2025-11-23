package com.bootcamp.demo.project_stock_data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;
import com.bootcamp.demo.project_stock_data.entity.StockEntity;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
  List<ProfileEntity> findByMarket(String market);

  Optional<ProfileEntity> findByStockEntity(StockEntity stockEntity);

  List<ProfileEntity> findByStockEntityIn(List<StockEntity> stockEntities);

  void deleteByStockEntity(StockEntity stockEntity);
}
