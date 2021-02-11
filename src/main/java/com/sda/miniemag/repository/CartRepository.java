package com.sda.miniemag.repository;

import com.sda.miniemag.model.Cart;
import com.sda.miniemag.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
//    Cart fiindCartByUser(User user);
//
//    List<Cart> findCartsForUser(User user);
    Optional<Cart> getCartByStatusAndUser(String status, User user);
    Optional<Cart> getCartByStatusAndUser_Id(String status,Long userId);
}

