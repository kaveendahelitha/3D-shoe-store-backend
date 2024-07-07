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


}
