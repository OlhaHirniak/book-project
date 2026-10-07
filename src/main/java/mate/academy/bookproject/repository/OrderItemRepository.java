package mate.academy.bookproject.repository;

import java.util.Optional;
import mate.academy.bookproject.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT с FROM OrderItem с WHERE с.order.id = :orderId AND с.id = :itemId")
    Optional<OrderItem> findOrderItemByIdInOrderById(@Param("orderId") Long orderId,
                                                     @Param("itemId") Long itemId);
}
