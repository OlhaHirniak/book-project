package mate.academy.bookproject.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.OrderItemsResponseDto;
import mate.academy.bookproject.dto.OrderRequestDto;
import mate.academy.bookproject.dto.OrderResponseDto;
import mate.academy.bookproject.dto.UpdateOrderStatusRequestDto;
import mate.academy.bookproject.exception.EntityNotFoundException;
import mate.academy.bookproject.mapper.OrderItemMapper;
import mate.academy.bookproject.mapper.OrderMapper;
import mate.academy.bookproject.model.CartItem;
import mate.academy.bookproject.model.Order;
import mate.academy.bookproject.model.OrderItem;
import mate.academy.bookproject.model.ShoppingCart;
import mate.academy.bookproject.model.Status;
import mate.academy.bookproject.model.User;
import mate.academy.bookproject.repository.CartItemRepository;
import mate.academy.bookproject.repository.OrderItemRepository;
import mate.academy.bookproject.repository.OrderRepository;
import mate.academy.bookproject.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find shoppingCart with user id: " + user.getId()));
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Can not find cart items with user id: "
                    + user.getId());
        }
        Order order = setUpNewOrder(orderRequestDto, user);
        Set<OrderItem> orderItems = convertCartItemsToOrderItems(shoppingCart,
                order);
        order.setOrderItems(orderItems);
        order.setTotal(calculateTotalPrice(order));
        orderRepository.save(order);
        shoppingCart.clearShoppingCart(shoppingCart);
        return orderMapper.toDto(order);
    }

    @Override
    public Set<OrderResponseDto> getByAllOrdersByUserId(Long userId) {
        Set<Order> order = orderRepository
                .findAllOrdersByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find orders with user id: " + userId));
        return order.stream().map(orderMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public OrderResponseDto updateOrderStatus(
            UpdateOrderStatusRequestDto updateOrderStatusRequestDto,
            Long orderId, User user) {
        Order order = orderRepository
                .findOrderWithIdByUserId(user.getId(), orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find order with user id: " + user.getId()
                                + "and order id: " + orderId));
        order.setStatus(updateOrderStatusRequestDto.getStatus());
        orderRepository.save(order);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderItemsResponseDto> getAllOrderItemsInOrder(Long orderId, Long userId) {
        Order order = orderRepository
                .findOrderWithIdByUserId(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find orders with user id: " + userId
                                + "and order id: " + orderId));
        return order.getOrderItems()
                .stream().map(orderItemMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public OrderItemsResponseDto getOrderItemInOrder(Long orderId, Long orderItemId, Long userId) {
        Order order = orderRepository.findOrderWithIdByUserId(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find orders with user id: " + userId
                                + "and order id: " + orderId));
        return orderItemMapper.toDto(orderItemRepository
                .findOrderItemByIdInOrderById(orderId, orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("Can "
                        + "not find orders with user id: " + userId)));
    }

    private Order setUpNewOrder(OrderRequestDto orderRequestDto, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(BigDecimal.ZERO);
        order.setStatus(Status.PENDING);
        return order;
    }

    private Set<OrderItem> convertCartItemsToOrderItems(ShoppingCart shoppingCart, Order order) {
        return shoppingCart.getCartItems().stream()
                .map(item -> {
                    OrderItem orderItem = orderItemMapper.toOrderItem(item);
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal::add).orElseThrow(() -> new EntityNotFoundException(
                        "Can't calculate total price"
                ));
    }
}

