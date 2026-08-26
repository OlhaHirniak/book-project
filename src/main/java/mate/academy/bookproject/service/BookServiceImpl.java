package mate.academy.bookproject.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.BookSearchParametersDto;
import mate.academy.bookproject.dto.CreateBookRequestDto;
import mate.academy.bookproject.exception.EntityNotFoundException;
import mate.academy.bookproject.mapper.BookMapper;
import mate.academy.bookproject.model.Book;
import mate.academy.bookproject.model.Category;
import mate.academy.bookproject.repository.BookRepository;
import mate.academy.bookproject.repository.BookSpecificationBuilder;
import mate.academy.bookproject.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toEntity(requestDto);

        if (requestDto.getCategoryIds() != null) {
            book.setCategories(getCategories(requestDto.getCategoryIds()));
        }

        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find a book with id" + id));
        return bookMapper.toDto(book);
    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
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

        if (requestDto.getCategoryIds() != null) {
            existingBook.setCategories(getCategories(requestDto.getCategoryIds()));
        }

        bookRepository.save(existingBook);
        return bookMapper.toDto(existingBook);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParametersDto);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private Set<Category> getCategories(List<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        if (categories.size() != categoryIds.size()) {
            throw new EntityNotFoundException("Some categories do not exist");
        }

        return new HashSet<>(categories);
    }
}
