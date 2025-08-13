package mate.academy.bookproject.repository;

import java.util.List;
import mate.academy.bookproject.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
