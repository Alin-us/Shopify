package com.sda.miniemag.controller;

import com.sda.miniemag.model.Cart;
import com.sda.miniemag.model.Product;
import com.sda.miniemag.repository.ProductRepository;
import com.sda.miniemag.repository.UserRepository;
import com.sda.miniemag.service.CartService;
import com.sda.miniemag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/show")
    public ModelAndView showCart() {
        ModelAndView modelAndView = new ModelAndView("customer/cart");
        modelAndView.addObject("cart", cartService.getOpenCart(userService.returnCurrentUser()));
        modelAndView.addObject("cart_item_count", cartService.getNumberOfCartItems(userService.returnCurrentUser()));
        return modelAndView;
    }

    @PostMapping("/add")
    private String addProductToCart(@RequestParam("product_id") Integer productId, @RequestParam("quantity") Integer quantity, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct.orElse(null);
        cartService.addCartItem(product, quantity, userService.returnCurrentUser());
        return "redirect:/products";
    }

    @PostMapping("/checkout")
    private String checkoutCart() {
        boolean success = cartService.checkout(userService.returnCurrentUser());
        if (success) {
            return "redirect:/";
        } else {
            return "redirect:/cart/show";
        }
    }

}

