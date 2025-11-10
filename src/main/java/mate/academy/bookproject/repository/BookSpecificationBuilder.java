package mate.academy.bookproject.repository;

import lombok.RequiredArgsConstructor;
import mate.academy.bookproject.dto.BookSearchParametersDto;
import mate.academy.bookproject.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> specification = Specification.unrestricted();
        if (searchParametersDto.author() != null && searchParametersDto.author().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParametersDto.author()));
        }
        if (searchParametersDto.title() != null && searchParametersDto.title().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParametersDto.title()));
        }
        return specification;
    }
}
