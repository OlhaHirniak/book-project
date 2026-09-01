package mate.academy.bookproject.service;

import mate.academy.bookproject.dto.CreateCartItemsRequestDto;
import mate.academy.bookproject.dto.ShoppingCartDto;
import mate.academy.bookproject.dto.UpdateCartItemRequestDto;
import mate.academy.bookproject.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto saveBooksToShoppingCart(CreateCartItemsRequestDto requestDto);

    ShoppingCartDto updateCartItem(UpdateCartItemRequestDto requestDto,
                                   Long cartItemId);

    void deleteCartItem(Long cartItemId);

    void addShoppingCartForNewUser(User user);
}
