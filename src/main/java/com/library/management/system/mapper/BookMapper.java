package com.library.management.system.mapper;

import com.library.management.system.dto.BookDTO;
import com.library.management.system.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authorId", source = "author.id")
    BookDTO toDTO(Book book);

    Book toEntity(BookDTO bookDTO);
}
