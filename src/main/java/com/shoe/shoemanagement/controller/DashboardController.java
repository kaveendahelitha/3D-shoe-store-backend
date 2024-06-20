package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.entity.ChartDataEntity;
import com.shoe.shoemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DashboardController {

   // @Autowired
  // private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ChartDataRepository chartDataRepository; // Autowire the ChartDataRepository

    @GetMapping("/api/v1/dashboard")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

       // data.put("productsCount", productRepository.count());
        data.put("categoriesCount", categoryRepository.count());
        data.put("customersCount", customerRepository.count());
        data.put("alertsCount", alertRepository.count());

        return data;
    }

    @GetMapping("/api/v1/chartData")
    public List<ChartData> getChartData() {
        // Fetch chart data from the database using ChartDataEntity
        List<ChartDataEntity> chartDataEntities = chartDataRepository.findAll();

        // Convert ChartDataEntity objects to ChartData objects
        List<ChartData> chartData = chartDataEntities.stream()
                .map(entity -> new ChartData(entity.getName(), entity.getUv(), entity.getPv(), entity.getAmt()))
                .collect(Collectors.toList());

        return chartData;
    }
}
