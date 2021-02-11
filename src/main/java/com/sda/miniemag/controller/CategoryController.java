package com.sda.miniemag.controller;

import com.sda.miniemag.model.Category;
import com.sda.miniemag.model.Product;
import com.sda.miniemag.repository.CategoryRepository;
import com.sda.miniemag.repository.ProductRepository;
import com.sda.miniemag.service.ShopService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopService shopService;

    @GetMapping("/list") //read
    public String categoryList(Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        return "admin/categories" ;
    }
    @GetMapping("/add") //create
    public String addCategory(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("parentCategories",categoryRepository.findAll());
        return "admin/add_category";
    }
    @PostMapping("/handleAdd")
    public String handleAddCategory(Category category){
        categoryRepository.save(category);
        return "redirect:/category/list";
    }
    @GetMapping("/update")
    public  String editCategory(@RequestParam("category_id") Integer categoriId,Model model){
        Optional<Category>optionalCategory=categoryRepository.findById(categoriId);
        Category category=optionalCategory.isPresent() ? optionalCategory.get() : new Category();
        model.addAttribute("category",category);
        model.addAttribute("parentCategories",categoryRepository.findAll());
        return "admin/add_category";

    }
    @PostMapping("/delete")
    public String delete(@RequestParam("category_id") Integer categoryId,Model model){
        categoryRepository.deleteById(categoryId);
        return "redirect:/category/list";
    }

}
