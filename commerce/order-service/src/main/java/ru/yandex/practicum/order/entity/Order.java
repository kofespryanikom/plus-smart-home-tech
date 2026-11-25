package ru.yandex.practicum.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String statusDetails;
    private LocalDateTime createdAt;
}
