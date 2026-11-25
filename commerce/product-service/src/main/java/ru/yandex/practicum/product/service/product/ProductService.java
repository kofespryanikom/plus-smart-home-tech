package ru.yandex.practicum.product.service.product;

import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto createProduct(CreateProductRequest createProductRequest);

    ProductDto getProductById(Long id);

    ProductDto patchProduct(Long id,
                            UpdateProductRequest updateProductRequest);

    List<ProductDto> searchProducts(String query);

    List<ProductDto> getProductsByCategory(Long categoryId);
}
