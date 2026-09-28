package mate.academy.bookproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.academy.bookproject.model.Status;

@Data
public class UpdateOrderStatusRequestDto {
    @NotNull
    private Status status;
}
