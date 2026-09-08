package mate.academy.bookproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemsRequestDto {
    @NotNull
    @Positive
    private Long bookId;

    @Positive
    private int quantity;
}
