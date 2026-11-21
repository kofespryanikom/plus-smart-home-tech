package ru.yandex.practicum.inventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.inventory.dto.*;
import ru.yandex.practicum.inventory.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryDto> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PutMapping
    public InventoryDto updateInventory(@RequestBody @Valid UpdateInventoryRequest updateInventoryRequest) {
        return inventoryService.updateInventory(updateInventoryRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDto createInventory(@RequestBody @Valid CreateInventoryRequest createInventoryRequest) {
        return inventoryService.createInventory(createInventoryRequest);
    }

    @PostMapping("/reserve")
    public ReserveResponse reserveInventory(@RequestBody @Valid ReserveRequest reserveRequest) {
        return inventoryService.reserveInventory(reserveRequest);
    }

    @GetMapping("/{productId}")
    public InventoryDto getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId);
    }
}
