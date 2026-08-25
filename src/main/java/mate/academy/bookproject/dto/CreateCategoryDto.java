package mate.academy.bookproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
