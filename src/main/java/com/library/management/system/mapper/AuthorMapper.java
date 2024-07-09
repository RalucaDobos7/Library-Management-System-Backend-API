package com.library.management.system.mapper;

import com.library.management.system.dto.author.AuthorDTO;
import com.library.management.system.dto.author.AuthorWithBooksDTO;
import com.library.management.system.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface AuthorMapper {

    AuthorWithBooksDTO toAuthorWIthBooksDTO(Author author);

    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);
}
