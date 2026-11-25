package ru.yandex.practicum.product.mapper;

import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.dto.CreateCategoryRequest;
import ru.yandex.practicum.product.entity.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toEntityFromCategoryDto(CategoryDto categoryById) {
        Category category = new Category();
        category.setId(categoryById.id());
        category.setName(categoryById.name());
        category.setDescription(categoryById.description());
        return category;
    }

    public static Category toEntityFromCreateCategoryRequest(CreateCategoryRequest createCategoryRequest) {
        Category category = new Category();
        category.setName(createCategoryRequest.name());
        category.setDescription(createCategoryRequest.description());
        return category;
    }
}
