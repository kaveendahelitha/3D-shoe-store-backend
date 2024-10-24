package com.shoe.shoemanagement.utils;






import com.shoe.shoemanagement.dto.ProductDTO;
import com.shoe.shoemanagement.dto.UserDTO;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


    public static UserDTO mapUserEntityToUserDTO(User user) {
       UserDTO userDTO=new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserFirstname(user.getUserFirstname());
        userDTO.setUserLastname(user.getUserLastname());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }


    public static ProductDTO mapProductEntityToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setCategory(product.getCategory());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductPhotoUrl(product.getProductPhotoUrl());
        productDTO.setProductColor(product.getProductColor());
        productDTO.setProductDescription(product.getProductDescription());
        return productDTO;
    }


//    public static ProductDTO mapProductEntityToProductDTOPlusOrders(Product product) {
//        ProductDTO productDTO = new ProductDTO();
//
//        productDTO.setId(product.getId());
//        productDTO.setProductName(product.getProductName());
//        productDTO.setCategory(product.getCategory());
//        productDTO.setProductPrice(product.getProductPrice());
//        productDTO.setProductPhotoUrl(product.getProductPhotoUrl());
//        productDTO.setProductColor(product.getProductColor());
//        productDTO.setProductDescription(product.getProductDescription());
//        return productDTO;
//
//       /* if (room.getBookings() != null) {
//            roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
//        }*/
//
//    }




    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<ProductDTO> mapProductListEntityToProductListDTO(List<Product> productList) {
        return productList.stream().map(Utils::mapProductEntityToProductDTO).
                collect(Collectors.toList());
    }

    public static Product mapProductDTOToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductPhotoUrl(productDTO.getProductPhotoUrl());
        product.setProductColor(productDTO.getProductColor());
        product.setProductDescription(productDTO.getProductDescription());
        return product;
    }


}
