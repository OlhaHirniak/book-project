package mate.academy.bookproject.mapper;

import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.UserRegistrationRequestDto;
import mate.academy.bookproject.dto.UserResponseDto;
import mate.academy.bookproject.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto userToUserDto(User user);

    User requestDtoToUser(UserRegistrationRequestDto userRegistrationRequestDto);
}
