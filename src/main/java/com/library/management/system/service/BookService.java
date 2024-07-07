package com.library.management.system.service;

import com.library.management.system.dto.BookDTO;
import com.library.management.system.entity.Book;
import com.library.management.system.exception.AuthorNotFoundException;
import com.library.management.system.exception.BookNotFoundException;
import com.library.management.system.mapper.BookMapper;
import com.library.management.system.repository.AuthorRepository;
import com.library.management.system.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDTO save(BookDTO bookDTO) {
        var author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException(HttpStatus.BAD_REQUEST.value()));

        var book = bookMapper.toEntity(bookDTO);
        book.setAuthor(author);

        bookRepository.save(book);

        return bookMapper.toDTO(book);
    }

    public BookDTO update(String bookId, BookDTO bookDTO) {
        var author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException(HttpStatus.BAD_REQUEST.value()));

        var bookOptional = bookRepository.findById(bookId);

        Book book;
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
            book.setTitle(bookDTO.getTitle());
            book.setDescription(bookDTO.getDescription());
            book.setIsbn(bookDTO.getIsbn());
            book.setAuthor(author);
        } else {
            book = bookMapper.toEntity(bookDTO);
            book.setId(bookId);
            book.setAuthor(author);
        }
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDTO).toList();
    }

    public BookDTO getById(String id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(HttpStatus.NOT_FOUND.value()));
        return bookMapper.toDTO(book);
    }
}
