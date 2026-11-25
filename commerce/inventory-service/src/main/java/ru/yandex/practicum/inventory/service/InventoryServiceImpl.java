package ru.yandex.practicum.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.inventory.dto.InventoryItemDto;
import ru.yandex.practicum.inventory.dto.InventoryItemRequest;
import ru.yandex.practicum.inventory.dto.ReserveRequest;
import ru.yandex.practicum.inventory.dto.ReserveResponse;
import ru.yandex.practicum.inventory.entity.InventoryItem;
import ru.yandex.practicum.inventory.exception.ConflictException;
import ru.yandex.practicum.inventory.exception.NotFoundException;
import ru.yandex.practicum.inventory.mapper.InventoryMapper;
import ru.yandex.practicum.inventory.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryItemDto> getAllInventory() {
        List<InventoryItem> inventoryItemList = inventoryRepository.findAll();

        return InventoryMapper.inventoryListToDto(inventoryItemList);
    }

    @Transactional
    @Override
    public InventoryItemDto updateInventoryItem(InventoryItemRequest inventoryItemRequest) {
        InventoryItemDto updatedInventoryItemDto = getInventoryItemByProductId(inventoryItemRequest.productId());
        InventoryItem updatedInventoryItem = InventoryMapper.dtoToEntity(updatedInventoryItemDto);

        updatedInventoryItem.setQuantity(inventoryItemRequest.quantity());
        updatedInventoryItem.setAvailableQuantity(updatedInventoryItem.getQuantity() - updatedInventoryItem.getReservedQuantity());

        return InventoryMapper.entityToDto(inventoryRepository.save(updatedInventoryItem));
    }

    @Transactional
    @Override
    public InventoryItemDto createInventoryItem(InventoryItemRequest inventoryItemRequest) {
        InventoryItem createdInventoryItem = new InventoryItem(null, inventoryItemRequest.productId(),
                inventoryItemRequest.quantity(), 0, inventoryItemRequest.quantity());

        return InventoryMapper.entityToDto(inventoryRepository.save(createdInventoryItem));
    }

    @Transactional
    @Override
    public ReserveResponse reserveInventoryItem(ReserveRequest reserveRequest) {
        getInventoryItemByProductId(reserveRequest.productId());

        int updatedRows = inventoryRepository.reserveStock(
                reserveRequest.productId(), reserveRequest.quantity());

        if (updatedRows == 0) {
            log.warn("Reservation exceeds available quantity of product with productId {}",
                    reserveRequest.productId());
            throw new ConflictException("Reservation exceeds available quantity of product with productId: " +
                    reserveRequest.productId());
        }

        int available = inventoryRepository.findAvailableQuantityByProductId(reserveRequest.productId());
        return new ReserveResponse(true, available,
                "Inventory with productId " + reserveRequest.productId() + " updated successfully");
    }

    @Override
    public InventoryItemDto getInventoryItemByProductId(Long productId) {
        InventoryItem inventoryItem = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    log.warn("Inventory not found for product ID: {}", productId);
                    throw new NotFoundException("Inventory not found for product ID: " + productId);
                });

        return InventoryMapper.entityToDto(inventoryItem);
    }
}
