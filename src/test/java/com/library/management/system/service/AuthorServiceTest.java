package com.library.management.system.service;

import com.library.management.system.config.BaseIntegrationTest;
import com.library.management.system.dto.author.AuthorDTO;
import com.library.management.system.dto.author.AuthorWithBooksDTO;
import com.library.management.system.dto.author.UpdateAuthorDTO;
import com.library.management.system.dto.book.BookDTO;
import com.library.management.system.entity.Author;
import com.library.management.system.entity.Book;
import com.library.management.system.exception.InvalidAuthorException;
import com.library.management.system.repository.AuthorRepository;
import com.library.management.system.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest extends BaseIntegrationTest {

    private static final String AUTHOR_TEST_ID = "test_id";
    private static final String AUTHOR_TEST_BIO = "test_bio";
    private static final String AUTHOR_TEST_NAME = "test_name";

    private static final String AUTHOR_NEW_BIO = "new_bio";
    private static final String AUTHOR_NEW_NAME = "new_name";
    private static final String AUTHOR_NEW_ID = "new_id";

    private static final String AUTHOR_1_ID = "a1_id";
    private static final String AUTHOR_1_BIO = "a1_bio";
    private static final String AUTHOR_1_NAME = "a1_name";

    private static final String BOOK_1_ID = "b1_id";
    private static final String BOOK_1_TITLE = "b1_title";
    private static final String BOOK_1_DESCRIPTION = "b1_description";

    private static final String BOOK_1_ISBN = "b1_isbn";

    private static final String BOOK_2_ID = "b2_id";
    private static final String BOOK_2_TITLE = "b2_title";
    private static final String BOOK_2_DESCRIPTION = "b2_description";
    private static final String BOOK_2_ISBN = "b2_isbn";

    private static final String AUTHOR_2_ID = "a2_id";
    private static final String AUTHOR_2_BIO = "a2_bio";
    private static final String AUTHOR_2_NAME = "a2_name";


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;


    @AfterEach
    void deleteAll() {
        authorRepository.deleteAll();
    }

    @Test
    void saveAuthor_shouldSaveAuthorCorrectly() {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(AUTHOR_TEST_ID);
        authorDTO.setBio(AUTHOR_TEST_BIO);
        authorDTO.setName(AUTHOR_TEST_NAME);

        var savedAuthor = authorService.save(authorDTO);

        assertEquals(AUTHOR_TEST_ID, savedAuthor.getId());
        assertEquals(AUTHOR_TEST_BIO, savedAuthor.getBio());
        assertEquals(AUTHOR_TEST_NAME, savedAuthor.getName());
    }

    @Test
    void updateExistingAuthor_shouldUpdateAuthorDetails() {
        var existingAuthor = new Author();
        existingAuthor.setBio(AUTHOR_TEST_BIO);
        existingAuthor.setName(AUTHOR_TEST_NAME);
        authorRepository.save(existingAuthor);

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setBio(AUTHOR_NEW_BIO);
        authorDTO.setName(AUTHOR_NEW_NAME);

        authorService.update(existingAuthor.getId(), authorDTO);


        Author updatedAuthor = authorRepository.findById(existingAuthor.getId()).get();
        assertEquals(AUTHOR_NEW_BIO, updatedAuthor.getBio());
        assertEquals(AUTHOR_NEW_NAME, updatedAuthor.getName());
    }

    @Test
    void updateNonExistingAuthor_shouldCreateNewAuthor() {
        var existingAuthor = new Author();
        existingAuthor.setBio(AUTHOR_TEST_BIO);
        existingAuthor.setName(AUTHOR_TEST_NAME);
        authorRepository.save(existingAuthor);

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setBio(AUTHOR_NEW_BIO);
        authorDTO.setName(AUTHOR_NEW_NAME);

        authorService.update(AUTHOR_NEW_ID, authorDTO);


        Author updatedAuthor = authorRepository.findById(AUTHOR_NEW_ID).get();
        assertEquals(AUTHOR_NEW_BIO, updatedAuthor.getBio());
        assertEquals(AUTHOR_NEW_NAME, updatedAuthor.getName());
        assertEquals(2, authorRepository.findAll().size());
    }

    @Test
    void patchExistingAuthor_shouldUpdateAuthorName() {
        var existingAuthor = new Author();
        existingAuthor.setName(AUTHOR_TEST_NAME);
        existingAuthor.setBio(AUTHOR_TEST_BIO);
        authorRepository.save(existingAuthor);

        UpdateAuthorDTO updateAuthorDTO = new UpdateAuthorDTO();
        updateAuthorDTO.setName(AUTHOR_NEW_NAME);

        authorService.patch(existingAuthor.getId(), updateAuthorDTO);


        Author updatedAuthor = authorRepository.findById(existingAuthor.getId()).get();
        assertEquals(AUTHOR_NEW_NAME, updatedAuthor.getName());
        assertEquals(AUTHOR_TEST_BIO, updatedAuthor.getBio());
    }

    @Test
    void patchNonExistingAuthor_shouldThrowException() {
        var existingAuthor = new Author();
        existingAuthor.setName(AUTHOR_TEST_NAME);
        existingAuthor.setBio(AUTHOR_TEST_BIO);
        authorRepository.save(existingAuthor);

        UpdateAuthorDTO updateAuthorDTO = new UpdateAuthorDTO();
        updateAuthorDTO.setName(AUTHOR_NEW_NAME);

        assertThrows(InvalidAuthorException.class, () -> authorService.patch(AUTHOR_NEW_ID, updateAuthorDTO));
    }

    @Test
    void deleteById_shouldDeleteAuthorSuccessfully() {
        var existingAuthor = new Author();
        existingAuthor.setName(AUTHOR_TEST_NAME);
        existingAuthor.setBio(AUTHOR_TEST_BIO);
        authorRepository.save(existingAuthor);

        var existingAuthorId = existingAuthor.getId();
        authorService.deleteById(existingAuthorId);

        assertTrue(authorRepository.findById(existingAuthorId).isEmpty());
    }

    @Test
    void getAllAuthors_shouldReturnAllAuthorsWithBooks() {
        var author1 = new Author();
        author1.setId(AUTHOR_1_ID);
        author1.setBio(AUTHOR_1_BIO);
        author1.setName(AUTHOR_1_NAME);
        authorRepository.save(author1);

        Book book1 = new Book();
        book1.setId(BOOK_1_ID);
        book1.setTitle(BOOK_1_TITLE);
        book1.setIsbn(BOOK_1_ISBN);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setId(BOOK_2_ID);
        book2.setTitle(BOOK_2_TITLE);
        book2.setIsbn(BOOK_2_ISBN);
        book2.setAuthor(author1);
        bookRepository.save(book2);

        var author2 = new Author();
        author2.setId(AUTHOR_2_ID);
        author2.setBio(AUTHOR_2_BIO);
        author2.setName(AUTHOR_2_NAME);
        authorRepository.save(author2);


        var allAuthors = authorService.getAllAuthors(Pageable.ofSize(3));

        assertThat(allAuthors)
                .hasSize(2)
                .extracting(AuthorWithBooksDTO::getId, author -> author.getBooks().size())
                .containsExactlyInAnyOrder(
                        tuple(AUTHOR_1_ID, 2),
                        tuple(AUTHOR_2_ID, 0)
                );
    }

    @Test
    void getAuthorById_shouldReturnAuthorWithBooks() {
        var author = new Author();
        author.setId(AUTHOR_1_ID);
        author.setBio(AUTHOR_1_BIO);
        author.setName(AUTHOR_1_NAME);
        authorRepository.save(author);

        Book book1 = new Book();
        book1.setId(BOOK_1_ID);
        book1.setTitle(BOOK_1_TITLE);
        book1.setDescription(BOOK_1_DESCRIPTION);
        book1.setIsbn(BOOK_1_ISBN);
        book1.setAuthor(author);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setId(BOOK_2_ID);
        book2.setTitle(BOOK_2_TITLE);
        book2.setDescription(BOOK_2_DESCRIPTION);
        book2.setIsbn(BOOK_2_ISBN);
        book2.setAuthor(author);
        bookRepository.save(book2);

        var authorWithBooks = authorService.getAuthorById(author.getId());

        assertEquals(author.getId(), authorWithBooks.getId());
        assertEquals(author.getBio(), authorWithBooks.getBio());
        assertEquals(author.getName(), authorWithBooks.getName());

        assertThat(authorWithBooks.getBooks())
                .hasSize(2)
                .extracting(BookDTO::getId, BookDTO::getTitle, BookDTO::getDescription, BookDTO::getIsbn)
                .containsExactlyInAnyOrder(
                        tuple(BOOK_1_ID, BOOK_1_TITLE, BOOK_1_DESCRIPTION, BOOK_1_ISBN),
                        tuple(BOOK_2_ID, BOOK_2_TITLE, BOOK_2_DESCRIPTION, BOOK_2_ISBN)
                );
    }

    @Test
    void getAuthorById_shouldThrowExceptionForNonExistingAuthor() {
        assertThrows(InvalidAuthorException.class, () -> authorService.getAuthorById(AUTHOR_NEW_ID));
    }
}