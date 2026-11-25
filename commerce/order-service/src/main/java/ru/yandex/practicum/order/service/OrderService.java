package ru.yandex.practicum.order.service;

import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrdersByEmail(String email);
}
