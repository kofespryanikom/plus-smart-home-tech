package ru.yandex.practicum.product.service.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.logging.annotation.Loggable;
import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;
import ru.yandex.practicum.product.entity.Product;
import ru.yandex.practicum.product.exception.NotFoundException;
import ru.yandex.practicum.product.mapper.CategoryMapper;
import ru.yandex.practicum.product.mapper.ProductMapper;
import ru.yandex.practicum.product.repository.ProductRepository;
import ru.yandex.practicum.product.service.category.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {

        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @Loggable
    public ProductDto createProduct(CreateProductRequest createProductRequest) {
        Product product = new Product();

        product.setName(createProductRequest.name());
        product.setDescription(createProductRequest.description());
        product.setPrice(createProductRequest.price());
        product.setCategory(CategoryMapper.toEntityFromCategoryDto(categoryService.getCategoryById(createProductRequest
                .categoryId())));
        product.setImageUrl(createProductRequest.imageUrl());
        product.setActive(true);

        return ProductMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto getProductById(@Positive(message = "Product ID must be a positive number") Long id) {
        Product productFound = productRepository.findById(id)
                .orElseGet(() -> {
                    log.warn("Product not found with id: {}", id);
                    throw new NotFoundException("Product not found with id: " + id);
                });

        return ProductMapper.toDto(productFound);
    }

    @Transactional
    @Override
    public ProductDto patchProduct(@Positive(message = "Product ID must be a positive number") Long id,
                                   UpdateProductRequest updateProductRequest) {
        ProductDto productDto = getProductById(id);

        Product product = ProductMapper.toEntity(productDto);

        product.setName(updateProductRequest.name() == null ? productDto.name() : updateProductRequest.name());
        product.setDescription(updateProductRequest.description() == null
                ? productDto.description() : updateProductRequest.description());
        product.setPrice(updateProductRequest.price() == null ? productDto.price() : updateProductRequest.price());
        product.setCategory(updateProductRequest.categoryId() == null ?
                CategoryMapper.toEntityFromCategoryDto(productDto.category()) :
                CategoryMapper.toEntityFromCategoryDto(categoryService
                        .getCategoryById(updateProductRequest.categoryId())));
        product.setImageUrl(updateProductRequest.imageUrl() == null
                ? productDto.imageUrl() : updateProductRequest.imageUrl());
        product.setActive(updateProductRequest.isActive() == null
                ? productDto.active() : updateProductRequest.isActive());

        return (ProductMapper.toDto(productRepository.save(product)));
    }

    @Override
    public List<ProductDto> searchProducts(@NotNull(message = "Query cannot be null") String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query).stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(
            @Positive(message = "Category ID must be a positive number") Long categoryId) {
        categoryService.getCategoryById(categoryId);

        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}
