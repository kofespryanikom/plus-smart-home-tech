package ru.yandex.practicum.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.OrderItemRequest;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderItem;
import ru.yandex.practicum.order.entity.OrderStatus;
import ru.yandex.practicum.order.exception.NotFoundException;
import ru.yandex.practicum.order.mapper.OrderItemMapper;
import ru.yandex.practicum.order.mapper.OrderMapper;
import ru.yandex.practicum.order.repository.OrderItemRepository;
import ru.yandex.practicum.order.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Order order = new Order(null, createOrderRequest.customerName(), createOrderRequest.customerEmail(),
                OrderStatus.CREATED, null, null, LocalDateTime.now());

        List<OrderItem> orderItemList = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal(0);

        for (OrderItemRequest orderItem : createOrderRequest.items()) {
            BigDecimal quantity = new BigDecimal(orderItem.quantity());
            totalPrice = totalPrice.add(orderItem.price().multiply(quantity));

            orderItemList.add(OrderItemMapper.fromRequestToEntity(orderItem, order));
        }

        order.setTotalPrice(totalPrice);

        order = orderRepository.save(order);

        orderItemRepository.saveAll(orderItemList);

        return orderMapper.entityToDto(order);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseGet(() -> {
            log.warn("Order not found with id {}", id);
            throw new NotFoundException("Order not found with id " + id);
        });

        return orderMapper.entityToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByEmail(String email) {
        return orderMapper.entityListToDtoList(orderRepository.findByCustomerEmail(email));
    }
}
