package ru.yandex.practicum.product.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;
import ru.yandex.practicum.product.service.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable @Positive(message = "Product ID must be a positive number") Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping("/{id}")
    public ProductDto patchProduct(@PathVariable @Positive(message = "Product ID must be a positive number") Long id,
                                   @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        return productService.patchProduct(id, updateProductRequest);
    }

    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDto> getProductsByCategory(@PathVariable
                                                  @Positive(message = "Category ID must be a positive number")
                                                  Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
}