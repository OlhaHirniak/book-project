package mate.academy.bookproject.service;

import java.util.List;
import mate.academy.bookproject.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
