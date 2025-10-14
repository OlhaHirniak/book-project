package mate.academy.bookproject.mapper;

import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.CreateBookRequestDto;
import mate.academy.bookproject.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto bookDtoToBookDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);
}
