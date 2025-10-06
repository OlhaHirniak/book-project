package mate.academy.bookproject.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.CreateBookRequestDto;
import mate.academy.bookproject.service.BookService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }
}
