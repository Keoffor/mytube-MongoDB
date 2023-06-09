package com.auth.ken.authjwt.controller;

import com.auth.ken.authjwt.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = {"${app.security.cors.origin}"})
@RestController
@RequestMapping("/products")
public class ProductController {
    private List<Product> products = new ArrayList<>();

    public ProductController() {
        products.add(Product.from("Men's Shoes (White)", "White color men's shoes", 100, "USD"));
        products.add(Product.from("TShirt (Blue)", "Blue color t-shirt", 55, "USD"));
        products.add(Product.from("TShirt (White)", "White color t-shirt", 50, "USD"));
        products.add(Product.from("Short (White)", "White color short", 60, "USD"));
        products.add(Product.from("Short (Black)", "Black color short", 55, "USD"));
    }

    @GetMapping
    public List<Product> getProducts() {
        return products;
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        if (StringUtils.isBlank(product.getName()) || StringUtils.isBlank(product.getDescription())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product name or description");
        }
        if (StringUtils.isBlank(product.getCurrency())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency");
        }
        if (product.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price should be non-negative");
        }
        product.setId(UUID.randomUUID().toString());
        products.add(product);
    }
}
