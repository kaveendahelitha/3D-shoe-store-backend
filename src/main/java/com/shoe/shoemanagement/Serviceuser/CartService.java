package com.shoe.shoemanagement.Serviceuser;

import com.shoe.shoemanagement.config.JWTAuthFilter;
import com.shoe.shoemanagement.repository.CartRepository;
import com.shoe.shoemanagement.repository.ProductRepo;
import com.shoe.shoemanagement.repository.UserRepo;
import com.shoe.shoemanagement.entity.Cart;
import com.shoe.shoemanagement.entity.Product;
import com.shoe.shoemanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartDao;

    @Autowired
    private ProductRepo productDao;

    @Autowired
    private UserRepo userDao;

    public void deleteCartItem(Long cartId) {
        cartDao.deleteById(cartId);
    }

    public Cart addToCart(Long productId) {
        Optional<Product> productOptional = productDao.findById(productId);
        if (!productOptional.isPresent()) {
            System.out.println("Product not found");
            return null;
        }
        Product product = productOptional.get();
        //return curent user
        String username = JWTAuthFilter.CURRENT_USER;

        if (username == null) {
            System.out.println("Current user is not authenticated");
            return null;
        }

        Optional<User> userOptional = userDao.findByEmail(username);
        if (!userOptional.isPresent()) {
            System.out.println("User not found");
            return null;
        }
        User user = userOptional.get();

        List<Cart> cartList = cartDao.findByUser(user);
        List<Cart> filteredList = cartList.stream()
                .filter(x -> x.getProduct().getId().equals(productId))
                .collect(Collectors.toList());

        if (!filteredList.isEmpty()) {
            System.out.println("Product already in cart");
            return null;
        }

        Cart cart = new Cart(product, user);
        //cart object
        Cart savedCart = cartDao.save(cart);

        if (savedCart == null) {
            System.out.println("Failed to save cart item");
            return null;
        }

        return savedCart;
    }
    //get cart details
    public List<Cart> getCartDetails() {
        String username = JWTAuthFilter.CURRENT_USER;

        if (username == null) {
            System.out.println("Current user is not authenticated");
            return null;
        }

        Optional<User> userOptional = userDao.findByEmail(username);
        if (!userOptional.isPresent()) {
            System.out.println("User not found");
            return null;
        }

        User user = userOptional.get();
        return cartDao.findByUser(user);
    }
}
