package com.geekbrains.miniapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductRepository productRepository;

    @Autowired
    public void setProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get a list of products
     * Example: GET http://localhost:8189/app/api/v1/products
     * */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get a list of products with a minimum price greater than or equal to 100
     * Example: GET http://localhost:8189/app/api/v1/products/filtered?min=100
     * */
    @GetMapping("/filtered")
    public List<Product> getAllProductsGreaterThanOrEqual(@RequestParam int min) {
        return productRepository.showAllProductsGreaterThanOrEqual(min);
    }

    /**
     * Get products by id
     * Example: GET http://localhost:8189/app/api/v1/products/2
     * */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

    /**
     * Change products title by id
     * Example: GET http://localhost:8189/app/api/v1/products/1/change_title?title=Milk2
     * */
    @GetMapping("/{id}/change_title")
    public void changeProductTitleById(@PathVariable Long id, @RequestParam String title) {
        Product product = productRepository.findById(id).get();
        product.setTitle(title);
        productRepository.save(product);
    }

    /**
     * Delete products by id
     * Example: GET http://localhost:8189/app/api/v1/products/delete/1
     * */
    @GetMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
