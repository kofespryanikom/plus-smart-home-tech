package ru.yandex.practicum.order.dto;

public record NotificationRequest(
        Long orderId,
        String email,
        String message
) {
}
