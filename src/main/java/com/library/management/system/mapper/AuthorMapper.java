package com.library.management.system.mapper;

import com.library.management.system.dto.AuthorDTO;
import com.library.management.system.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface AuthorMapper {

    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);
}
