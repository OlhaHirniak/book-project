package mate.academy.bookproject.service;

import java.util.List;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.BookSearchParametersDto;
import mate.academy.bookproject.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto getBookById(Long id);

    List<BookDto> findAll();

    void deleteById(Long id);

    BookDto updateBookById(CreateBookRequestDto requestDto, Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto);
}
