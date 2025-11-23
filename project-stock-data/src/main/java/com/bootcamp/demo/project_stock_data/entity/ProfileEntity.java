package com.bootcamp.demo.project_stock_data.entity;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProfileEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 2, nullable = false)
  private String market; // US, HK
  @Column(length = 200, nullable = false)
  private String exchange;
  @Column(length = 100, nullable = false)
  private String industry;
  @Column(name = "ipo_date", nullable = false)
  private LocalDate ipoDate;
  private String name;
  @Column(name = "web_url", length = 100, nullable = false)
  private String webUrl;
  @Column(nullable = false)
  private String logo;
  @Column(name = "share_outstanding", nullable = false)
  private Double shareOutstanding;
  @Column(name = "market_cap", nullable = false)
  private Double marketCap;
  @Column(nullable = false)
  private String symbol;

  @Setter
  @ManyToOne // ! Should be @OneToOne
  @JoinColumn(name = "stock_id", nullable = false)
  private StockEntity stockEntity;
}
