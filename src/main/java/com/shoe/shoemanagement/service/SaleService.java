package com.shoe.shoemanagement.service;

import com.shoe.shoemanagement.exceptions.ResourceNotFoundException;
import com.shoe.shoemanagement.entity.Sale;
import com.shoe.shoemanagement.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, Sale saleDetails) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found for this id :: " + id));

        sale.setItemName(saleDetails.getItemName());
        sale.setGivenPrice(saleDetails.getGivenPrice());
        sale.setSoldPrice(saleDetails.getSoldPrice());
        sale.setProfit(saleDetails.getProfit());
        sale.setDate(saleDetails.getDate());

        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found for this id :: " + id));
        saleRepository.delete(sale);
    }
}
