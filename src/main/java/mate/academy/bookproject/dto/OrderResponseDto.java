package mate.academy.bookproject.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import mate.academy.bookproject.model.Status;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemsResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;
}
