package com.library.management.system.dto.book;

import com.library.management.system.validator.ISBN;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String id;

    @NotNull
    private String title;

    private String description;

    @NotNull
    @ISBN
    private String isbn;

    @NotNull
    private String authorId;
}
