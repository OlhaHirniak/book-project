package mate.academy.bookproject.service;

import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.UserRegistrationRequestDto;
import mate.academy.bookproject.dto.UserResponseDto;
import mate.academy.bookproject.exception.RegistrationException;
import mate.academy.bookproject.mapper.UserMapper;
import mate.academy.bookproject.model.User;
import mate.academy.bookproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user, email already in use");
        }
        User user = userMapper.requestDtoToUser(userRegistrationRequestDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }
}
