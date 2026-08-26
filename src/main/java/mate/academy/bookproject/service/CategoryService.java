package mate.academy.bookproject.service;

import java.util.List;
import mate.academy.bookproject.dto.BookDtoWithoutCategoryIds;
import mate.academy.bookproject.dto.CategoryDto;
import mate.academy.bookproject.dto.CreateCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryDto categoryDto);

    CategoryDto update(Long id, CreateCategoryDto categoryDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}
