package com.sda.miniemag.service;

import com.sda.miniemag.model.Cart;
import com.sda.miniemag.model.CartItem;
import com.sda.miniemag.model.Product;
import com.sda.miniemag.model.User;
import com.sda.miniemag.repository.CartItemRepository;
import com.sda.miniemag.repository.CartRepository;
import com.sda.miniemag.repository.ProductRepository;
import com.sda.miniemag.repository.UserRepository;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public void addCartItem(Product product, Integer requestedQuantity, User user) {
        Cart cart = getOpenCart(user);
        Optional<CartItem> optionalCartItem = cartItemRepository.getCartItemByProductAndCart(product, cart);
        CartItem cartItem = optionalCartItem.orElse(new CartItem(product, 0, cart));
        if (product.getQuantity() >= cartItem.getQuantity() + requestedQuantity) {
            cartItem.setQuantity(cartItem.getQuantity() + requestedQuantity);
        }
        if (cartItem.getQuantity() <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItemRepository.save(cartItem);
        }

    }

    public Cart getOpenCart(User user) {
        Optional<Cart> optionalCart = cartRepository.getCartByStatusAndUser("open", user);
        Cart cart = optionalCart.orElse(new Cart(user, "open"));
        return cartRepository.save(cart);
    }

    public Integer getNumberOfCartItems(User user) {//fiseaza in navbar cantitatea produselor
        Cart cart = getOpenCart(user);
        int itemSum = 0;
//        rtItem cartItem: cart)
        for (int i = 0; i < cartItemRepository.getCartItemsByCart(cart).size(); i++) {
            itemSum += cartItemRepository.getCartItemsByCart(cart).get(i).getQuantity();

        }
        return itemSum;
//        return cartItemRepository.getCartItemsByCart(cart).size();//afiseaza doar produsele in cart in navbar

    }

    public boolean checkout(User user) {
        Cart cart = getOpenCart(user);

        if (cart.getCartItemSet().size() > 0) {
            //cart is valid
            boolean allElementsValid = true;
            for (CartItem cartItem : cart.getCartItemSet()) {
                if (cartItem.getQuantity() > productRepository.getOne(cartItem.getProduct().getId()).getQuantity())
                    allElementsValid = false;
            }
            if (allElementsValid) {
                for (CartItem cartItem : cart.getCartItemSet()) {
                    Product product = cartItem.getProduct();
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    productRepository.save(product);
                }
                cart.setStatus("closed");
                cartRepository.save(cart);
            }
            return allElementsValid;

        }
        return false;
    }

}
