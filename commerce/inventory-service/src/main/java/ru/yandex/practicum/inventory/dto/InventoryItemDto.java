package ru.yandex.practicum.inventory.dto;

public record InventoryItemDto(

        Long id,

        Long productId,

        Integer quantity,

        Integer reservedQuantity,

        Integer availableQuantity,

        Long version
) {
}
