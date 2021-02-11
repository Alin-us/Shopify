package com.sda.miniemag.service;

import com.sda.miniemag.model.Product;
import com.sda.miniemag.repository.CategoryRepository;
import com.sda.miniemag.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void addProduct(Product product){
        productRepository.save(product);
    }


}
