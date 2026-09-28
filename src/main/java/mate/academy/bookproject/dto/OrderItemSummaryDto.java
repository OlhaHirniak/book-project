package mate.academy.bookproject.dto;

import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import mate.academy.bookproject.model.OrderItem;

@AllArgsConstructor
@Data
public class OrderItemSummaryDto {
    private Set<OrderItem> orderItems;
    private BigDecimal totalPrice;
}
