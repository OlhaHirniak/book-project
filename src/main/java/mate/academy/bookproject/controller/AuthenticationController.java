package mate.academy.bookproject.controller;

import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.UserRegistrationRequestDto;
import mate.academy.bookproject.dto.UserResponseDto;
import mate.academy.bookproject.exception.RegistrationException;
import mate.academy.bookproject.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto registerUser(@RequestBody UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
