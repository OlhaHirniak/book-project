package mate.academy.bookproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.CreateCartItemsRequestDto;
import mate.academy.bookproject.dto.ShoppingCartDto;
import mate.academy.bookproject.dto.UpdateCartItemRequestDto;
import mate.academy.bookproject.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Shopping cart for user")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get a shopping cart", description = "Get a"
            + " shopping card of the authenticated user")
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add to shopping cart", description = "Add book to shopping card")
    public ShoppingCartDto addToCart(@RequestBody @Valid CreateCartItemsRequestDto requestDto) {
        return shoppingCartService.saveBooksToShoppingCart(requestDto);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/items/{cartItemId}")
    @Operation(
            summary = "Update cart item",
            description = "Update the quantity of a cart item"
    )
    public ShoppingCartDto updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto) {
        return shoppingCartService.updateCartItem(requestDto, cartItemId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete cart item",
            description = "Remove a book from the shopping cart"
    )
    public void deleteCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.deleteCartItem(cartItemId);
    }
}
