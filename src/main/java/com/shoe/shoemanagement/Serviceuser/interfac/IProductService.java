package com.shoe.shoemanagement.Serviceuser.interfac;



import com.shoe.shoemanagement.dto.PriceLevelDTO;
import com.shoe.shoemanagement.dto.ProductDTO;
import com.shoe.shoemanagement.dto.ReqRes;

import java.util.List;

public interface IProductService {

    List<String> getAllProductsCategories();
    List<String> getAllProductColors();
    List<PriceLevelDTO> getAllProductsPriceLevels();
    ReqRes getProductsByColorPriceAndCategory(String category, String productColor, String priceRange);

    ReqRes getAllProducts();
    ReqRes addProduct(ProductDTO productDTO);
    ReqRes getProductById(Long id);
    ReqRes updateProduct(Long id, ProductDTO productDTO);
    ReqRes deleteProduct(Long id);


}
