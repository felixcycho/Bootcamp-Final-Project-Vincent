package com.bootcamp.demo.project_stock_data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bootcamp.demo.project_stock_data.model.dto.ProfileDTO;
import com.bootcamp.demo.project_stock_data.entity.ProfileEntity;
import com.bootcamp.demo.project_stock_data.mapper.EntityMapper;
import com.bootcamp.demo.project_stock_data.repository.ProfileRepository;
import com.bootcamp.demo.project_stock_data.service.StockService;

@Component
public class SchedulerRunner {
  @Autowired
  private StockService stockService;
  
  @Autowired
  private EntityMapper entityMapper;

  @Autowired
  private ProfileRepository profileRepository;

  @Scheduled(fixedDelay = 60000) // 1min
  public void loadProfiles() {
    // Load the profiles, according to the latest active stocks
    this.stockService.getActiveStocks("US").stream() //
        .forEach(se -> {
          try {
            if (this.profileRepository.findByStockEntity(se).isPresent()) {
              System.out.println(se.getSymbol() + " exists stock_profiles");
              return;
            }
            ProfileDTO profileDTO =
                this.stockService.getFinnhubProfile(se.getSymbol());
            Thread.sleep(2000L);
            ProfileEntity profileEntity = this.entityMapper.map(profileDTO);
            profileEntity.setStockEntity(se);
            this.stockService.saveProfile(profileEntity);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
            throw new RuntimeException("Interrupted while sleeping", e);
          }
        });
  }
}
