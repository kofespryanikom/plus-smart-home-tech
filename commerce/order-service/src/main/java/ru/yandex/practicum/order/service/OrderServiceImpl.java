package ru.yandex.practicum.order.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.OrderItemRequest;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderItem;
import ru.yandex.practicum.order.exception.NotFoundException;
import ru.yandex.practicum.order.mapper.OrderItemMapper;
import ru.yandex.practicum.order.mapper.OrderMapper;
import ru.yandex.practicum.order.repository.OrderItemRepository;
import ru.yandex.practicum.order.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.entityListToDtoList(orderRepository.findAll());
    }

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        BigDecimal totalPrice = new BigDecimal(0);

        for (OrderItemRequest orderItem : createOrderRequest.items()) {
            BigDecimal quantity = new BigDecimal(orderItem.quantity());
            totalPrice = totalPrice.add(orderItem.price().multiply(quantity));
        }

        Order order = new Order(null, createOrderRequest.customerName(), createOrderRequest.customerEmail(),
                "CREATED", totalPrice, null, LocalDateTime.now());

        Order orderSaved = orderRepository.save(order);

        List<OrderItem> orderItemList = createOrderRequest.items().stream()
                .map(orderItemRequest -> OrderItemMapper.fromRequestToEntity(orderItemRequest, orderSaved))
                .toList();

        orderItemRepository.saveAll(orderItemList);

        return orderMapper.entityToDto(orderSaved);
    }

    @Override
    public OrderDto getOrderById(@Positive(message = "Order ID must be a positive number") Long id) {
        Order order = orderRepository.findById(id).orElseGet(() -> {
            log.warn("Order not found with id {}", id);
            throw new NotFoundException("Order not found with id " + id);
        });

        return orderMapper.entityToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByEmail(@Email(message = "Email should be email-like format") String email) {
        return orderMapper.entityListToDtoList(orderRepository.findByCustomerEmail(email));
    }
}
