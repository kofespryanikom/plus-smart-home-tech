package ru.yandex.practicum.product.service.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto createProduct(CreateProductRequest createProductRequest);

    ProductDto getProductById(@Positive(message = "Product ID must be a positive number") Long id);

    ProductDto patchProduct(@Positive(message = "Product ID must be a positive number") Long id,
                            UpdateProductRequest updateProductRequest);

    List<ProductDto> searchProducts(@NotNull(message = "Query cannot be null") String query);

    List<ProductDto> getProductsByCategory(@Positive(message = "Category ID must be a positive number") Long categoryId);
}
