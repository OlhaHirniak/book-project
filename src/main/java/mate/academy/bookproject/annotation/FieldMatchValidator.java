package mate.academy.bookproject.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import mate.academy.bookproject.dto.UserRegistrationRequestDto;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegistrationRequestDto dto,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (dto == null) {
            return true;
        }
        return Objects.equals(dto.getPassword(), dto.getRepeatPassword());
    }
}
