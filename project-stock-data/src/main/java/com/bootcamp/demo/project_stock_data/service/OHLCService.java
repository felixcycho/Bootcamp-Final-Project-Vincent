package com.bootcamp.demo.project_stock_data.service;

import java.util.List;
import com.bootcamp.demo.project_stock_data.entity.OHLCEntity;

public interface OHLCService {
  List<OHLCEntity> getOHLCs(Long stockId);
}
