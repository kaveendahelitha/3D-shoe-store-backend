package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.Serviceuser.interfac.IProductService;
import com.shoe.shoemanagement.dto.PriceLevelDTO;
import com.shoe.shoemanagement.dto.ProductDTO;
import com.shoe.shoemanagement.dto.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ReqRes> getAllProducts() {
        ReqRes reqRes = productService.getAllProducts();
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }

    @GetMapping("/categories")
    public List<String> getAllProductsCategories() {
        return productService.getAllProductsCategories();
    }

    @GetMapping("/colors")
    public List<String> getAllProductColors() {
        return productService.getAllProductColors();
    }

    @GetMapping("/priceranges")
    public List<PriceLevelDTO> getAllProductsPriceLevels() {
        return productService.getAllProductsPriceLevels();
    }

    @GetMapping("/available-products-by-color-category-and-pricerange")
    public ResponseEntity<ReqRes> getAvailableRoomsByDateAndType(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productColor,
            @RequestParam(required = false) String priceRange
    ) {
        if (category == null || category.isBlank() || productColor == null
                || productColor.isBlank() || priceRange == null || priceRange.isBlank()) {
            ReqRes response = new ReqRes();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (category, productColor, priceRange)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        ReqRes reqRes = productService.getProductsByColorPriceAndCategory(category, productColor, priceRange);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }

    @PostMapping("/add")
    public ResponseEntity<ReqRes> addProduct(
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart("productPhoto") MultipartFile productPhoto) {
        ReqRes reqRes = productService.addProduct(productDTO, productPhoto);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ReqRes> getProductById(@PathVariable Long id) {
        ReqRes reqRes = productService.getProductById(id);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }

    @PutMapping("/product-update/{id}")
    public ResponseEntity<ReqRes> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart("productPhoto") MultipartFile productPhoto) {
        ReqRes reqRes = productService.updateProduct(id, productDTO, productPhoto);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }

    @DeleteMapping("/product-delete/{id}")
    public ResponseEntity<ReqRes> deleteProduct(@PathVariable Long id) {
        ReqRes reqRes = productService.deleteProduct(id);
        return ResponseEntity.status(reqRes.getStatusCode()).body(reqRes);
    }
}
