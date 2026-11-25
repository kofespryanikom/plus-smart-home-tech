package ru.yandex.practicum.order.mapper;

import ru.yandex.practicum.order.dto.OrderItemDto;
import ru.yandex.practicum.order.dto.OrderItemRequest;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {
    public static OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    public static List<OrderItemDto> entityListToDto(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(OrderItemMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static OrderItem fromRequestToEntity(OrderItemRequest orderItemRequest, Order order) {
        return new OrderItem(null,
                orderItemRequest.productId(),
                orderItemRequest.productName(),
                orderItemRequest.quantity(),
                orderItemRequest.price(),
                order);
    }
}
