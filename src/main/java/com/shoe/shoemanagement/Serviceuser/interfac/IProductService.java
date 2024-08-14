package com.shoe.shoemanagement.Serviceuser.interfac;



import com.shoe.shoemanagement.dto.PriceLevelDTO;
import com.shoe.shoemanagement.dto.ProductDTO;
import com.shoe.shoemanagement.dto.ReqRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<String> getAllProductsCategories();
    List<String> getAllProductColors();
    List<PriceLevelDTO> getAllProductsPriceLevels();
    ReqRes getProductsByColorPriceAndCategory(String category, String productColor, String priceRange);

    ReqRes getAllProducts();

    ReqRes getProductById(Long id);

    ReqRes deleteProduct(Long id);


    ReqRes addProduct(ProductDTO productDTO, MultipartFile productPhoto);

    ReqRes updateProduct(Long id,  ProductDTO productDTO, MultipartFile productPhoto);
}
