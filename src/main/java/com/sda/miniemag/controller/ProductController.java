
package com.sda.miniemag.controller;
import com.sda.miniemag.model.Product;
import com.sda.miniemag.repository.CategoryRepository;
import com.sda.miniemag.repository.ProductRepository;
import com.sda.miniemag.service.PriceComparator;
import com.sda.miniemag.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ShopService shopService;

    @GetMapping("/list") //read
    private String productList(Model model) {
        List<Product> productList=productRepository.findAll();
        productList.sort(new PriceComparator());
        model.addAttribute("products", productList);
        // model.addAttribute("categories",categoryRepository.findAll());
        return "admin/products";
    }

    @GetMapping("/")
    private String homePage() {
        return "index";
    }

    @GetMapping("/add") //create
    private String showAddProductPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/add_product";
    }

    @PostMapping("/handleAdd")
    private String handleAddProduct(Product product) {
        productRepository.save(product);
        return "redirect:/product/list";
    }

    @GetMapping("/update") //update
    private String editProduct(@RequestParam("product_id") Integer productId, Model model) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.isPresent() ? productOptional.get() : new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/add_product";
    }


    @PostMapping("/delete") //delete
    private String delete(@RequestParam("product_id") Integer productId, Model model) {
        productRepository.deleteById(productId);
        return "redirect:/product/list";
    }
}