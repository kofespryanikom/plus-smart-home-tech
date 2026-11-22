package ru.yandex.practicum.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String query1, String query2);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAllByActive(Boolean isActive);
}
