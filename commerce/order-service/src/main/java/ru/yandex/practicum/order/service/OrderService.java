package ru.yandex.practicum.order.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;

import java.util.List;

@Validated
public interface OrderService {
    List<OrderDto> getAllOrders();

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    OrderDto getOrderById(@Positive(message = "Order ID must be a positive number") Long orderId);

    List<OrderDto> getOrdersByEmail(@Email(message = "Email should be email-like format") String email);
}
