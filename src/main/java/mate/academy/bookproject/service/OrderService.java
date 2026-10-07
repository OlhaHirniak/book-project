package mate.academy.bookproject.service;

import java.util.Set;
import mate.academy.bookproject.dto.OrderItemsResponseDto;
import mate.academy.bookproject.dto.OrderRequestDto;
import mate.academy.bookproject.dto.OrderResponseDto;
import mate.academy.bookproject.dto.UpdateOrderStatusRequestDto;
import mate.academy.bookproject.model.User;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user);

    Set<OrderResponseDto> getByAllOrdersByUserId(Long userId);

    OrderResponseDto updateOrderStatus(UpdateOrderStatusRequestDto updateOrderStatusRequestDto,
                                       Long orderId, User user);

    Set<OrderItemsResponseDto> getAllOrderItemsInOrder(Long orderId, Long userId);

    OrderItemsResponseDto getOrderItemInOrder(Long orderId, Long orderItemId, Long userId);
}
