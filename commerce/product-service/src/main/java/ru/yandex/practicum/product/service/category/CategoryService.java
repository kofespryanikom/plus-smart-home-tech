package ru.yandex.practicum.product.service.category;

import jakarta.validation.constraints.Positive;
import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.dto.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(@Positive(message = "Category ID must be a positive number") Long id);

    CategoryDto createCategory(CreateCategoryRequest createCategoryRequest);
}
