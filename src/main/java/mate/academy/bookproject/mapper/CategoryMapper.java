package mate.academy.bookproject.mapper;

import mate.academy.bookproject.config.MapperConfig;
import mate.academy.bookproject.dto.CategoryDto;
import mate.academy.bookproject.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
