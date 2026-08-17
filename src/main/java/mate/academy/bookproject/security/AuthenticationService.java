package mate.academy.bookproject.security;

import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.UserLoginRequestDto;
import mate.academy.bookproject.dto.UserLoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto
                        .email(), userLoginRequestDto.password())
        );

        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
