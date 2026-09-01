package mate.academy.bookproject.repository;

import mate.academy.bookproject.model.ShoppingCart;
import mate.academy.bookproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUser(User user);
}
