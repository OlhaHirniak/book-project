package mate.academy.bookproject.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.UserRegistrationRequestDto;
import mate.academy.bookproject.dto.UserResponseDto;
import mate.academy.bookproject.exception.EntityNotFoundException;
import mate.academy.bookproject.exception.RegistrationException;
import mate.academy.bookproject.mapper.UserMapper;
import mate.academy.bookproject.model.Role;
import mate.academy.bookproject.model.User;
import mate.academy.bookproject.repository.RoleRepository;
import mate.academy.bookproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user, email already in use"
            + userRegistrationRequestDto.getEmail());
        }
        User user = userMapper.requestDtoToUser(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole(Role.RoleName.USER).orElseThrow(()
                -> new EntityNotFoundException("Role " + Role.RoleName.USER
                + " not found"));
        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);
        shoppingCartService.addShoppingCartForNewUser(savedUser);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user by email: " + email
                ));
    }
}
