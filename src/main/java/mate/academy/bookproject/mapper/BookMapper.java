package mate.academy.bookproject.mapper;

import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.BookDto;
import mate.academy.bookproject.dto.BookDtoWithoutCategoryIds;
import mate.academy.bookproject.dto.CreateBookRequestDto;
import mate.academy.bookproject.model.Book;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toEntity(CreateBookRequestDto bookDto);

    @Mapping(target = "categories", ignore = true)
    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(
            @MappingTarget BookDto bookDto,
            Book book
    ) {
        bookDto.setCategoryIds(
                book.getCategories()
                        .stream()
                        .map(category -> category.getId())
                        .collect(java.util.stream.Collectors.toSet())
        );
    }
}
