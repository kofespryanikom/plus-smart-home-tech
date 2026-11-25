package ru.yandex.practicum.order.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.OrderItemDto;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderItem;
import ru.yandex.practicum.order.repository.OrderItemRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderItemRepository orderItemRepository;

    public OrderDto entityToDto(Order order) {
        List<OrderItem> orderItemsList = orderItemRepository.findByOrder_Id(order.getId());
        List<OrderItemDto> orderItemsDtosList = OrderItemMapper.entityListToDto(orderItemsList);

        return new OrderDto(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getStatusDetails(),
                order.getCreatedAt(),
                orderItemsDtosList
        );
    }

    public List<OrderDto> entityListToDtoList(List<Order> orders) {
        List<Long> orderIds = orders.stream()
                .map(Order::getId)
                .toList();
        List<OrderItem> orderItemList = orderItemRepository.findByOrderIdIn(orderIds);


        Map<Long, List<OrderItemDto>> orderItemDtoMap = new HashMap<>();

        for (OrderItem orderItem : orderItemList) {
            orderItemDtoMap.computeIfAbsent(orderItem.getOrder().getId(), k -> new ArrayList<>())
                    .add(OrderItemMapper.entityToDto(orderItem));
        }

        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(order.getId(),
                    order.getCustomerName(),
                    order.getCustomerEmail(),
                    order.getStatus(),
                    order.getTotalPrice(),
                    order.getStatusDetails(),
                    order.getCreatedAt(),
                    orderItemDtoMap.get(order.getId())
            );

            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}
