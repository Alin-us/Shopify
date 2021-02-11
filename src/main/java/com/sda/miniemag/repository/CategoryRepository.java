package com.sda.miniemag.repository;

import com.sda.miniemag.model.Category;
import com.sda.miniemag.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category,Integer> {
}
