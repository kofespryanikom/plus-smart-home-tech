package ru.yandex.practicum.inventory.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.inventory.dto.InventoryItemDto;
import ru.yandex.practicum.inventory.dto.InventoryItemRequest;
import ru.yandex.practicum.inventory.dto.ReserveRequest;
import ru.yandex.practicum.inventory.dto.ReserveResponse;
import ru.yandex.practicum.inventory.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Validated
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryItemDto> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PutMapping
    public InventoryItemDto updateInventory(@RequestBody @Valid InventoryItemRequest updateInventoryItemRequest) {
        return inventoryService.updateInventoryItem(updateInventoryItemRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryItemDto createInventoryItem(@RequestBody @Valid InventoryItemRequest inventoryItemRequest) {
        return inventoryService.createInventoryItem(inventoryItemRequest);
    }

    @PostMapping("/reserve")
    public ReserveResponse reserveInventoryItem(@RequestBody @Valid ReserveRequest reserveRequest) {
        return inventoryService.reserveInventoryItem(reserveRequest);
    }

    @GetMapping("/{productId}")
    public InventoryItemDto getInventoryItemByProductId(@PathVariable @Positive(message = "Inventory id must be positive")
                                                        Long productId) {
        return inventoryService.getInventoryItemByProductId(productId);
    }
}
