package com.library.management.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.system.dto.book.BookDTO;
import com.library.management.system.dto.book.UpdateBookDTO;
import com.library.management.system.exception.InvalidBookException;
import com.library.management.system.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final String BOOK_ID = "6196154c-e736-460e-92e8-0aba1c285e99";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void createBook_shouldReturnCreatedStatus_whenBookIsValid() throws Exception {

        BookDTO book = BookDTO.builder()
                .title("book_title")
                .description("book_description")
                .isbn("978-0-596-52068-7")
                .authorId("book_authorId")
                .build();

        mvc.perform(
                MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(book))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createBook_shouldReturnBadRequest_whenIsbnIsInvalid() throws Exception {

        BookDTO book = BookDTO.builder()
                .title("book_title")
                .description("book_description")
                .isbn("wrong_isbn")
                .authorId("book_authorId")
                .build();

        mvc.perform(
                        MockMvcRequestBuilders.post("/books")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(book))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("Invalid ISBN number"));
    }

    @Test
    void createBook_shouldReturnBadRequest_whenTitleAndAuthorIdAreNull() throws Exception {
        BookDTO book = BookDTO.builder()
                .title(null)
                .description("book_description")
                .isbn("978-0-596-52068-7")
                .authorId(null)
                .build();

        mvc.perform(
                        MockMvcRequestBuilders.post("/books")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(book))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("must not be null"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId").value("must not be null"));
    }


    @Test
    void updateBook_shouldReturnOkStatus_whenBookIsValid() throws Exception {
        BookDTO book = BookDTO.builder()
                .title("book_title")
                .description("book_description")
                .isbn("978-0-596-52068-7")
                .authorId("book_authorId")
                .build();

        mvc.perform(
                        MockMvcRequestBuilders.put("/books/" + BOOK_ID)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(book))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void patchBook_shouldReturnOkStatus_whenBookTitleIsUpdated() throws Exception {
        UpdateBookDTO book = new UpdateBookDTO();
        book.setTitle("new_book_title");

        mvc.perform(
                        MockMvcRequestBuilders.patch("/books/" + BOOK_ID)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(book))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void patchBook_shouldReturnNotFoundStatus_whenBookDoesNotExist() throws Exception {
        UpdateBookDTO book = new UpdateBookDTO();
        book.setTitle("new_book_title");


        when(bookService.patch(BOOK_ID, book)).thenThrow(new InvalidBookException(HttpStatus.NOT_FOUND.value()));

        mvc.perform(
                        MockMvcRequestBuilders.patch("/books/" + BOOK_ID)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(book))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Book is not present for given id"));
    }

    @Test
    void deleteBook_shouldReturnNoContentStatus_whenBookIsDeleted() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.delete("/books/" + BOOK_ID)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteBook_shouldReturnNotFoundStatus_whenBookDoesNotExist() throws Exception {

        doThrow(new InvalidBookException(HttpStatus.NOT_FOUND.value())).when(bookService).deleteById(BOOK_ID);

        mvc.perform(
                        MockMvcRequestBuilders.delete("/books/" + BOOK_ID)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Book is not present for given id"));
    }

    @Test
    void getAllBooks_shouldReturnOkStatus() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.get("/books")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getBookById_shouldReturnOkStatus_whenBookExists() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.get("/books/" + BOOK_ID)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getBookById_shouldReturnNotFoundStatus_whenBookDoesNotExist() throws Exception {
        when(bookService.getById(BOOK_ID)).thenThrow(new InvalidBookException(HttpStatus.NOT_FOUND.value()));

        mvc.perform(
                        MockMvcRequestBuilders.get("/books/" + BOOK_ID)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Book is not present for given id"));
    }
}