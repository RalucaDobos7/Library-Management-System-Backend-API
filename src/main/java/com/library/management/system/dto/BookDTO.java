package com.library.management.system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String id;
    private String title;
    private String description;
    private String isbn;
    private String authorId;
}
