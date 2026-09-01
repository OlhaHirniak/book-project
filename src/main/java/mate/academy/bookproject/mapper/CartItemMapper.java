package mate.academy.bookproject.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.CartItemsResponseDto;
import mate.academy.bookproject.dto.CreateCartItemsRequestDto;
import mate.academy.bookproject.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemsResponseDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "book", ignore = true)
    CartItem toModel(CreateCartItemsRequestDto requestDto);

    @Named("getCartItemsResponseDto")
    default Set<CartItemsResponseDto> getResponseDto(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
