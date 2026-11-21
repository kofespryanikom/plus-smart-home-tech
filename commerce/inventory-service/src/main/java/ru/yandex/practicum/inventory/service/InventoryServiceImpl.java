package ru.yandex.practicum.inventory.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.inventory.dto.*;
import ru.yandex.practicum.inventory.entity.Inventory;
import ru.yandex.practicum.inventory.exception.ConflictException;
import ru.yandex.practicum.inventory.exception.NotFoundException;
import ru.yandex.practicum.inventory.mapper.InventoryMapper;
import ru.yandex.practicum.inventory.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
@Transactional(readOnly = true)
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryDto> getAllInventory() {
        List<Inventory> inventory = inventoryRepository.findAll();

        return InventoryMapper.inventoryListToDto(inventory);
    }

    @Transactional
    @Override
    public InventoryDto updateInventory(UpdateInventoryRequest updateInventoryRequest) {
        InventoryDto updatedInventoryDto = getInventoryByProductId(updateInventoryRequest.productId());
        Inventory updatedInventory = InventoryMapper.dtoToEntity(updatedInventoryDto);

        updatedInventory.setQuantity(updateInventoryRequest.quantity());
        updatedInventory.setAvailableQuantity(updatedInventory.getQuantity() - updatedInventory.getReservedQuantity());

        return InventoryMapper.entityToDto(inventoryRepository.save(updatedInventory));
    }

    @Transactional
    @Override
    public InventoryDto createInventory(CreateInventoryRequest createInventoryRequest) {
        try {
            getInventoryByProductId(createInventoryRequest.productId());

            log.warn("Conflict with existing inventory with same productId: {}", createInventoryRequest.productId());
            throw new ConflictException("Conflict with existing inventory with same productId: " +
                    createInventoryRequest.productId());
        } catch (NotFoundException ignored) {
        }

        Inventory createdInventory = new Inventory(null, createInventoryRequest.productId(),
                createInventoryRequest.quantity(), 0, createInventoryRequest.quantity());

        return InventoryMapper.entityToDto(inventoryRepository.save(createdInventory));
    }

    @Transactional
    @Override
    public ReserveResponse reserveInventory(ReserveRequest reserveRequest) {
        InventoryDto updatedInventoryDto = getInventoryByProductId(reserveRequest.productId());
        Inventory updatedInventory = InventoryMapper.dtoToEntity(updatedInventoryDto);

        ReserveResponse inventoryReserveResponse;

        try {
            updatedInventory.setReservedQuantity(reserveRequest.quantity());
            updatedInventory.setAvailableQuantity(updatedInventory.getAvailableQuantity() -
                    updatedInventory.getReservedQuantity());

            if (updatedInventory.getAvailableQuantity() < 0) {
                log.warn("Reservation exceeds available quantity of product with productId {}",
                        reserveRequest.productId());
                throw new ConflictException("Reservation exceeds available quantity of product with productId: " +
                        reserveRequest.productId());
            }

            inventoryReserveResponse = new ReserveResponse(true, updatedInventory.getAvailableQuantity(),
                    "Inventory with productId " + reserveRequest.productId() + " updated successfully");
        } catch (OptimisticLockException e) {
            inventoryReserveResponse = new ReserveResponse(false, updatedInventory.getAvailableQuantity(),
                    "Inventory of product with productId " + reserveRequest.productId() +
                            " was modified by another transaction");

            log.warn("Inventory of product with productId {} was modified by another transaction",
                    reserveRequest.productId());
        }

        return inventoryReserveResponse;
    }

    @Override
    public InventoryDto getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    log.warn("Inventory not found for product ID: {}", productId);
                    throw new NotFoundException("Inventory not found for product ID: " + productId);
                });

        return InventoryMapper.entityToDto(inventory);
    }

}
