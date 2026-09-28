package mate.academy.bookproject.mapper;

import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.OrderItemsResponseDto;
import mate.academy.bookproject.model.CartItem;
import mate.academy.bookproject.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemsResponseDto toDto(OrderItem orderItem);

    @Mapping(source = "book.price", target = "price")
    @Mapping(target = "id", ignore = true)
    OrderItem convertCartItemToOrderItem(CartItem cartItem);
}
