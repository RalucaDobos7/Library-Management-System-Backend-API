package com.library.management.system.dto.author;

import com.library.management.system.dto.book.BookDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorDTO {

    private String id;

    @NotNull
    private String name;

    private String bio;

    private List<BookDTO> books;
}
