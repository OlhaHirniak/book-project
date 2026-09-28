package mate.academy.bookproject.dto;

import lombok.Data;

@Data
public class OrderItemsResponseDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
