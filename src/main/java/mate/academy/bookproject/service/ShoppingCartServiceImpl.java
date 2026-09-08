package mate.academy.bookproject.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.CreateCartItemsRequestDto;
import mate.academy.bookproject.dto.ShoppingCartDto;
import mate.academy.bookproject.dto.UpdateCartItemRequestDto;
import mate.academy.bookproject.exception.EntityNotFoundException;
import mate.academy.bookproject.mapper.CartItemMapper;
import mate.academy.bookproject.mapper.ShoppingCartMapper;
import mate.academy.bookproject.model.Book;
import mate.academy.bookproject.model.CartItem;
import mate.academy.bookproject.model.ShoppingCart;
import mate.academy.bookproject.model.User;
import mate.academy.bookproject.repository.BookRepository;
import mate.academy.bookproject.repository.CartItemRepository;
import mate.academy.bookproject.repository.ShoppingCartRepository;
import mate.academy.bookproject.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getShoppingCart() {
        ShoppingCart shoppingCart = getShoppingCartByUser();
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto saveBooksToShoppingCart(CreateCartItemsRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCartByUser();

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cannot find book by id " + requestDto.getBookId()));
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems()
                .stream().filter(cartItem -> cartItem.getBook().getId()
                        .equals(requestDto.getBookId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(
                    cartItem.getQuantity() + requestDto.getQuantity()
            );
        } else {
            CartItem cartItem = cartItemMapper.toModel(requestDto);
            cartItem.setBook(book);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
        }

        shoppingCartRepository.save(shoppingCart);
        return getShoppingCart();
    }

    @Override
    public ShoppingCartDto updateCartItem(UpdateCartItemRequestDto requestDto,
                                          Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartByUser();
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                        shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cartItem by id " + cartItemId));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return getShoppingCart();
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartByUser();
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                        shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cartItem by id " + cartItemId));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void addShoppingCartForNewUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getShoppingCartByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find shopping cart for user id " + user.getId()
                ));
    }
}
