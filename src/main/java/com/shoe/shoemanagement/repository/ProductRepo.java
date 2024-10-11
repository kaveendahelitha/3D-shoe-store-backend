package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.dto.PriceLevelDTO;
import com.shoe.shoemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();

    @Query("SELECT DISTINCT p.productColor FROM Product p")
    List<String> findDistinctColors();

    @Query("SELECT new com.shoe.shoemanagement.dto.PriceLevelDTO(" +
            "CASE " +
            "WHEN p.productPrice > 10000 THEN '10000+' " +
            "WHEN p.productPrice > 7000 THEN '7000-10000' " +
            "WHEN p.productPrice > 5000 THEN '5000-7000' " +
            "WHEN p.productPrice > 3000 THEN '3000-5000' " +
            "WHEN p.productPrice > 2000 THEN '2000-3000' " +
            "WHEN p.productPrice > 1000 THEN '1000-2000' " +
            "ELSE '<1000' END, " +
            "COUNT(p)) " +
            "FROM Product p " +
            "GROUP BY " +
            "CASE " +
            "WHEN p.productPrice > 10000 THEN '10000+' " +
            "WHEN p.productPrice > 7000 THEN '7000-10000' " +
            "WHEN p.productPrice > 5000 THEN '5000-7000' " +
            "WHEN p.productPrice > 3000 THEN '3000-5000' " +
            "WHEN p.productPrice > 2000 THEN '2000-3000' " +
            "WHEN p.productPrice > 1000 THEN '1000-2000' " +
            "ELSE '<1000' END")
    List<PriceLevelDTO> findProductPriceLevels();

    @Query("SELECT p FROM Product p WHERE " +
            "p.productColor LIKE %:productColor% AND " +
            "p.priceRange LIKE %:priceRange% AND " +
            "p.category LIKE %:category%")
    List<Product> findProductsByColorPriceAndCategory(String category, String productColor, String priceRange);
    List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(String key1, String key2);


}
