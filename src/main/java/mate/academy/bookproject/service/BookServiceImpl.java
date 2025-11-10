package mate.academy.bookproject.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.BookSearchParametersDto;
import mate.academy.bookproject.dto.CreateBookRequestDto;
import mate.academy.bookproject.exception.EntityNotFoundException;
import mate.academy.bookproject.mapper.BookMapper;
import mate.academy.bookproject.model.Book;
import mate.academy.bookproject.repository.BookRepository;
import mate.academy.bookproject.repository.BookSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        bookRepository.save(book);
        return bookMapper.bookDtoToBookDto(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find a book with id" + id));
        return bookMapper.bookDtoToBookDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookDtoToBookDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBookById(CreateBookRequestDto requestDto, Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cannot find the book with id: " + id));
        bookMapper.updateBookFromDto(requestDto, existingBook);
        bookRepository.save(existingBook);
        return bookMapper.bookDtoToBookDto(existingBook);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParametersDto);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::bookDtoToBookDto)
                .toList();
    }
}
