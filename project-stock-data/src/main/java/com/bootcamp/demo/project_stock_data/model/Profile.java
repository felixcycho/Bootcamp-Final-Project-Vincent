package com.bootcamp.demo.project_stock_data.model;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Profile {
  private Long id;
  private String symbol; // ! No symbol in Entity
  private String type; // S=STOCK, C="Crypto"
  private String market; // US, HK
  private String exchange;
  private String industry;
  private LocalDate ipoDate;
  private String name;
  private String webUrl;
  private String logo;
}
