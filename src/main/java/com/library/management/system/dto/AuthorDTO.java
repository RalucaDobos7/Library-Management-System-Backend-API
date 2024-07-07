package com.library.management.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO {

    private String id;
    private String name;
    private String bio;
    private List<BookDTO> books;
}
