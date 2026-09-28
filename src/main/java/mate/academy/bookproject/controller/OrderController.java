package mate.academy.bookproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.OrderItemsResponseDto;
import mate.academy.bookproject.dto.OrderRequestDto;
import mate.academy.bookproject.dto.OrderResponseDto;
import mate.academy.bookproject.dto.UpdateOrderStatusRequestDto;
import mate.academy.bookproject.model.User;
import mate.academy.bookproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Order for user")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an order", description = "Create a new order")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                        @AuthenticationPrincipal User user) {
        return orderService.createOrder(orderRequestDto, user);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get all the orders", description = "Get all the orders by user")
    public Set<OrderResponseDto> getOrders(@AuthenticationPrincipal User user) {
        return orderService.getByAllOrdersByUserId(user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status", description = "Update order status by user")
    public OrderResponseDto updateStatus(@RequestBody UpdateOrderStatusRequestDto
                                         updateOrderStatusRequestDto,
                                         @PathVariable Long id,
                                         @AuthenticationPrincipal User user) {
        return orderService.updateOrderStatus(updateOrderStatusRequestDto, id, user);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all the items", description = "Get all the items in user order")
    public Set<OrderItemsResponseDto> getAllItems(@PathVariable Long id,
                                                  @AuthenticationPrincipal User user) {
        return orderService.getAllOrderItemsInOrder(id, user.getId());
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get a specific items", description = "Get a specific in user order")
    public OrderItemsResponseDto getAnItem(@PathVariable Long id,
                                           @PathVariable Long itemId,
                                           @AuthenticationPrincipal User user) {
        return orderService.getOrderItemInOrder(id, itemId, user.getId());
    }
}
