package mate.academy.bookproject.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.exception.DataProcessingException;
import mate.academy.bookproject.model.Book;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey()
                .equals(key)).findFirst()
                .orElseThrow(() -> new DataProcessingException("Cant find key: " + key));
    }
}
