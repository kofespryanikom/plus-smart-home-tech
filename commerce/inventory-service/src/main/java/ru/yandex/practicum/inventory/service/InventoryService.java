package ru.yandex.practicum.inventory.service;

import jakarta.validation.constraints.Positive;
import ru.yandex.practicum.inventory.dto.*;

import java.util.List;

public interface InventoryService {
    List<InventoryDto> getAllInventory();

    InventoryDto updateInventory(UpdateInventoryRequest updateInventoryRequest);

    InventoryDto createInventory(CreateInventoryRequest createInventoryRequest);

    ReserveResponse reserveInventory(ReserveRequest reserveRequest);

    InventoryDto getInventoryByProductId(@Positive(message = "Inventory id must be positive") Long productId);
}
