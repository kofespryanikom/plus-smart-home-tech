package ru.yandex.practicum.inventory.mapper;

import ru.yandex.practicum.inventory.dto.InventoryItemDto;
import ru.yandex.practicum.inventory.entity.InventoryItem;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryMapper {
    public static InventoryItemDto entityToDto(InventoryItem inventoryItem) {
        return new InventoryItemDto(
                inventoryItem.getId(),
                inventoryItem.getProductId(),
                inventoryItem.getQuantity(),
                inventoryItem.getReservedQuantity(),
                inventoryItem.getAvailableQuantity(),
                inventoryItem.getVersion()
        );
    }

    public static List<InventoryItemDto> inventoryListToDto(List<InventoryItem> inventoryItemList) {
        return inventoryItemList.stream()
                .map(InventoryMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static InventoryItem dtoToEntity(InventoryItemDto inventoryItemDto) {
        return new InventoryItem(
                inventoryItemDto.id(),
                inventoryItemDto.productId(),
                inventoryItemDto.quantity(),
                inventoryItemDto.reservedQuantity(),
                inventoryItemDto.availableQuantity(),
                inventoryItemDto.version()
        );
    }
}