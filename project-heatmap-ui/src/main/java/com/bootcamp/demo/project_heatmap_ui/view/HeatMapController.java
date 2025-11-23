package com.bootcamp.demo.project_heatmap_ui.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HeatMapController {
  @GetMapping("/stockmap")
  public String getStockMap(Model model) {
    return "stockmap"; // html
  }
  @GetMapping("/ohlc")
  public String getOHLC(Model model) {
    return "ohlc"; // html
  }
}
