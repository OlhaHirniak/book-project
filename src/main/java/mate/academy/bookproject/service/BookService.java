package mate.academy.bookproject.service;

import java.util.List;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.BookSearchParametersDto;
import mate.academy.bookproject.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto getBookById(Long id);

    Page<BookDto> getAll(Pageable pageable);

    void deleteById(Long id);

    BookDto updateBookById(CreateBookRequestDto requestDto, Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto);
}
