package com.example.products.controller;

import com.example.products.model.Products;
import com.example.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // Khởi tạo danh sách Product
    public ProductController() {
    }

    // Trả về danh sách Products
    @GetMapping("/products")
    @ResponseBody
    public List<Products> getProductList() {
        return productService.findAll();
    }

    // Trả về một Product cụ thể theo ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable("id") int productId) {
        // Không cần @ResponseBody vì ResponseEntity đã bao gồm body
        for (Products product : productService.findAll()) {
            if (product.getProductId() == productId) {
                return ResponseEntity.status(200).body(product);
            }
        }
        return ResponseEntity.status(404).body(null);  // Trả về lỗi 404 nếu không tìm thấy
    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public List<Products> removeProductById(@PathVariable("id") Long productId) {
        productService.delete(productId);
        return productService.findAll();
    }

    @PostMapping("/product")
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        productService.save(product);
        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable("id") Long productId, @RequestBody Products updateProduct) {
        Products product = productService.findById(productId);
        if (product != null) {
            // Cập nhật các trường mới
            product.setProductName(updateProduct.getProductName());
            product.setDescription(updateProduct.getDescription());
            product.setPrice(updateProduct.getPrice());
            product.setStock(updateProduct.getStock());
            product.setCategoryId(updateProduct.getCategoryId());
            product.setImageUrl(updateProduct.getImageUrl());
            // Lưu sản phẩm đã cập nhật
            productService.save(product);
            return ResponseEntity.status(200).body(product);
        }
        return ResponseEntity.status(404).body(null);
    }

}
