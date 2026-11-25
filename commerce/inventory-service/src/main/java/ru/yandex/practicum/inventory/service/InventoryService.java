package ru.yandex.practicum.inventory.service;

import ru.yandex.practicum.inventory.dto.InventoryItemDto;
import ru.yandex.practicum.inventory.dto.InventoryItemRequest;
import ru.yandex.practicum.inventory.dto.ReserveRequest;
import ru.yandex.practicum.inventory.dto.ReserveResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryItemDto> getAllInventory();

    InventoryItemDto updateInventoryItem(InventoryItemRequest inventoryItemRequest);

    InventoryItemDto createInventoryItem(InventoryItemRequest inventoryItemRequest);

    ReserveResponse reserveInventoryItem(ReserveRequest reserveRequest);

    InventoryItemDto getInventoryItemByProductId(Long productId);
}
