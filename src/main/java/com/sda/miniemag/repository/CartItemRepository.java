package com.sda.miniemag.repository;

import com.sda.miniemag.model.Cart;
import com.sda.miniemag.model.CartItem;
import com.sda.miniemag.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> getCartItemByProductAndCart(Product product, Cart cart);

    List<CartItem> getCartItemsByCart(Cart cart);

}

