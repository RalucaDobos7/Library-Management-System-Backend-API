package com.library.management.system.dto.book;

import com.library.management.system.validator.ISBN;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookDTO {

    private String title;

    private String description;

    @ISBN
    private String isbn;

    private String authorId;
}
