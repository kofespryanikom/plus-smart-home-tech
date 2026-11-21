package ru.yandex.practicum.product.mapper;

import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.entity.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                CategoryMapper.toDto(product.getCategory()),
                product.getImageUrl(),
                product.getActive()
        );
    }

    public static Product toEntity(ProductDto productDto) {
        return new Product(
                productDto.id(),
                productDto.name(),
                productDto.description(),
                productDto.price(),
                CategoryMapper.toEntityFromCategoryDto(productDto.category()),
                productDto.imageUrl(),
                productDto.active()
        );
    }
}
