package ru.yandex.practicum.inventory.mapper;

import ru.yandex.practicum.inventory.dto.InventoryDto;
import ru.yandex.practicum.inventory.entity.Inventory;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryMapper {
    public static InventoryDto entityToDto(Inventory inventory) {
        return new InventoryDto(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getQuantity(),
                inventory.getReservedQuantity(),
                inventory.getAvailableQuantity(),
                inventory.getVersion()
        );
    }

    public static List<InventoryDto> inventoryListToDto(List<Inventory> inventoryList) {
        return inventoryList.stream()
                .map(InventoryMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static Inventory dtoToEntity(InventoryDto inventoryDto) {
        return new Inventory(
                inventoryDto.id(),
                inventoryDto.productId(),
                inventoryDto.quantity(),
                inventoryDto.reservedQuantity(),
                inventoryDto.availableQuantity(),
                inventoryDto.version()
        );
    }
}