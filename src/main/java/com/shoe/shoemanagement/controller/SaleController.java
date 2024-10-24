package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.entity.Sale;
import com.shoe.shoemanagement.Serviceuser.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/v1")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Sale sale = saleService.getSaleById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found for this id :: " + id));
        return ResponseEntity.ok().body(sale);
    }

    @PostMapping("/sales")
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody Sale saleDetails) {
        Sale updatedSale = saleService.updateSale(id, saleDetails);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales/chartData")
    public List<ChartData> getChartData() {
        List<Sale> sales = saleService.getAllSales();
        return sales.stream()
                .map(sale -> new ChartData(sale.getItemName(), sale.getGivenPrice(), sale.getProfit()))
                .collect(Collectors.toList());
    }

    static class ChartData {
        private String name;
        private double givenPrice;
        private double profit;

        public ChartData(String name, double givenPrice, double profit) {
            this.name = name;
            this.givenPrice = givenPrice;
            this.profit = profit;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getGivenPrice() {
            return givenPrice;
        }

        public void setGivenPrice(double givenPrice) {
            this.givenPrice = givenPrice;
        }

        public double getProfit() {
            return profit;
        }

        public void setProfit(double profit) {
            this.profit = profit;
        }
    }
}
