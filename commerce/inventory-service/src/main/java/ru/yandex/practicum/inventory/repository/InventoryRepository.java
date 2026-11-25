package ru.yandex.practicum.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.inventory.entity.InventoryItem;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByProductId(Long productId);

    @Modifying
    @Query("""
            UPDATE InventoryItem i
            SET i.reservedQuantity = i.reservedQuantity + :quantity,
                i.availableQuantity = i.availableQuantity - :quantity
            WHERE i.productId = :productId
              AND i.availableQuantity >= :quantity
            """)
    int reserveStock(@Param("productId") Long productId, @Param("quantity") int quantity);

    @Query("SELECT i.availableQuantity FROM InventoryItem i WHERE i.productId = :productId")
    int findAvailableQuantityByProductId(@Param("productId") Long productId);
}
