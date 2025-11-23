package com.bootcamp.demo.project_stock_data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.demo.project_stock_data.entity.OHLCEntity;
import com.bootcamp.demo.project_stock_data.entity.StockEntity;

@Repository
public interface OHLCRepository extends JpaRepository<OHLCEntity, Long> {
  List<OHLCEntity> findByStockEntity(StockEntity stockEntity);
}
