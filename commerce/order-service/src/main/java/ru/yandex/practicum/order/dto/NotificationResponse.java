package ru.yandex.practicum.order.dto;

public record NotificationResponse(
        Long id,
        Long orderId,
        String status
) {
}