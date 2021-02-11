package com.sda.miniemag.controller;

import com.sda.miniemag.repository.CategoryRepository;
import com.sda.miniemag.repository.ProductRepository;
import com.sda.miniemag.service.CartService;
import com.sda.miniemag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    private String homePage(Model model) {
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("cart_item_count", cartService.getNumberOfCartItems(userService.returnCurrentUser()));
        model.addAttribute("topProducts",productRepository.findAll().subList(0,Math.min(4,productRepository.findAll().size())));
        return "customer/index";
    }
    @GetMapping("/products")
    private String products(Model model) {
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("cart_item_count",cartService.getNumberOfCartItems(userService.returnCurrentUser()));
        return "customer/products";
    }


}
