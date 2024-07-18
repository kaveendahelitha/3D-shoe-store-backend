package com.shoe.shoemanagement.Serviceuser.impl;



import com.shoe.shoemanagement.Serviceuser.interfac.IProductService;
import com.shoe.shoemanagement.dto.PriceLevelDTO;
import com.shoe.shoemanagement.dto.ProductDTO;
import com.shoe.shoemanagement.dto.ReqRes;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.repository.ProductRepo;
import com.shoe.shoemanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<String> getAllProductsCategories() {
        return productRepo.findDistinctCategories();
    }

    @Override
    public List<String> getAllProductColors() {
        return productRepo.findDistinctColors();
    }




    @Override
    public List<PriceLevelDTO> getAllProductsPriceLevels() {
        return productRepo.findProductPriceLevels();
    }

    @Override
    public ReqRes getProductsByColorPriceAndCategory(String category, String productColor,
                                                     String priceRange) {

        ReqRes reqRes = new ReqRes();
        try {
            List<Product> availableProducts = productRepo.
                    findProductsByColorPriceAndCategory(category,productColor,priceRange);
            List<ProductDTO> productDTOList =
                    Utils.mapProductListEntityToProductListDTO(availableProducts);
            reqRes.setStatusCode(200);
            reqRes.setMessage("successful");
            reqRes.setProductList(productDTOList);
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error finding product " + e.getMessage());
        }


        return reqRes;
    }

    @Override
    public ReqRes getAllProducts() {
        ReqRes reqRes = new ReqRes();

        try {
            List<Product> productList = productRepo.findAll
                    (Sort.by(Sort.Direction.DESC, "id"));
            List<ProductDTO> productDTOList = Utils.mapProductListEntityToProductListDTO(productList);
            reqRes.setStatusCode(200);
            reqRes.setMessage("successful");
            reqRes.setProductList(productDTOList);

        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error finding product " + e.getMessage());
        }
        return reqRes;
    }

    @Override
    public ReqRes addProduct(ProductDTO productDTO) {
        ReqRes reqRes = new ReqRes();
        try {
            Product product = Utils.mapProductDTOToProductEntity(productDTO);
            productRepo.save(product);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Product added successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error adding product: " + e.getMessage());
        }
        return reqRes;
    }

    @Override
    public ReqRes getProductById(Long id) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Product> product = productRepo.findById(id);
            if (product.isPresent()) {
                ProductDTO productDTO = Utils.mapProductEntityToProductDTO(product.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("Product found successfully");
                reqRes.setProduct(productDTO);
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Product not found");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error finding product: " + e.getMessage());
        }
        return reqRes;
    }

    @Override
    public ReqRes updateProduct(Long id, ProductDTO productDTO) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Product> productOptional = productRepo.findById(id);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setProductName(productDTO.getProductName());
                product.setCategory(productDTO.getCategory());
                product.setProductPrice(productDTO.getProductPrice());
                product.setProductPhotoUrl(productDTO.getProductPhotoUrl());
                product.setProductColor(productDTO.getProductColor());
                product.setProductDescription(productDTO.getProductDescription());
                productRepo.save(product);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Product updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Product not found");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error updating product: " + e.getMessage());
        }
        return reqRes;
    }

    @Override
    public ReqRes deleteProduct(Long id) {
        ReqRes reqRes = new ReqRes();
        try {
            if (productRepo.existsById(id)) {
                productRepo.deleteById(id);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Product deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Product not found");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error deleting product: " + e.getMessage());
        }
        return reqRes;
    }
}
