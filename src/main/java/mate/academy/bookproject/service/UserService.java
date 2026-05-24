package mate.academy.bookproject.service;

import mate.academy.bookproject.dto.UserRegistrationRequestDto;
import mate.academy.bookproject.dto.UserResponseDto;
import mate.academy.bookproject.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
