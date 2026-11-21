package ru.yandex.practicum.product.service.category;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.dto.CreateCategoryRequest;
import ru.yandex.practicum.product.entity.Category;
import ru.yandex.practicum.product.exception.NotFoundException;
import ru.yandex.practicum.product.mapper.CategoryMapper;
import ru.yandex.practicum.product.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(@Positive(message = "Category ID must be a positive number") Long id) {
        Category category = categoryRepository.findById(id)
                .orElseGet(() -> {
                    log.warn("Category with id: {} not found", id);
                    throw new NotFoundException("Category with id: " + id + " not found");
                });
        return CategoryMapper.toDto(category);
    }

    @Transactional
    @Override
    public CategoryDto createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = CategoryMapper.toEntityFromCreateCategoryRequest(createCategoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDto(savedCategory);
    }
}
