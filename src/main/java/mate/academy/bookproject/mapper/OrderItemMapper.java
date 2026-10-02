package mate.academy.bookproject.mapper;

import java.math.BigDecimal;
import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.OrderItemsResponseDto;
import mate.academy.bookproject.model.Book;
import mate.academy.bookproject.model.CartItem;
import mate.academy.bookproject.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemsResponseDto toDto(OrderItem orderItem);

    @Mapping(source = "book", target = "price", qualifiedByName = "bookPrice")
    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(CartItem cartItem);

    @Named("bookPrice")
    static BigDecimal getBookPrice(Book book) {
        return book.getPrice();
    }
}
