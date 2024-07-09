package com.library.management.system.service;

import com.library.management.system.dto.book.BookDTO;
import com.library.management.system.dto.book.UpdateBookDTO;
import com.library.management.system.entity.Book;
import com.library.management.system.exception.InvalidAuthorException;
import com.library.management.system.exception.InvalidBookException;
import com.library.management.system.exception.IsbnConflictException;
import com.library.management.system.mapper.BookMapper;
import com.library.management.system.repository.AuthorRepository;
import com.library.management.system.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDTO save(BookDTO bookDTO) {
        var author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new InvalidAuthorException(HttpStatus.BAD_REQUEST.value()));
        checkDuplicateIsbn(bookDTO.getIsbn());
        var book = bookMapper.toEntity(bookDTO);
        book.setAuthor(author);

        bookRepository.save(book);

        return bookMapper.toDTO(book);
    }

    public BookDTO update(String bookId, BookDTO bookDTO) {
        var author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new InvalidAuthorException(HttpStatus.BAD_REQUEST.value()));

        var bookOptional = bookRepository.findById(bookId);

        Book book;
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
            book.setTitle(bookDTO.getTitle());
            book.setDescription(bookDTO.getDescription());
            if(!bookDTO.getIsbn().equals(book.getIsbn())) {
                checkDuplicateIsbn(bookDTO.getIsbn());
            }
            book.setIsbn(bookDTO.getIsbn());
            book.setAuthor(author);
        } else {
            checkDuplicateIsbn(bookDTO.getIsbn());
            book = bookMapper.toEntity(bookDTO);
            book.setId(bookId);
            book.setAuthor(author);
        }
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO patch(String bookId, UpdateBookDTO bookDTO) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new InvalidBookException(HttpStatus.NOT_FOUND.value()));

        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getDescription() != null) {
            book.setDescription(bookDTO.getDescription());
        }
        if (bookDTO.getIsbn() != null) {
            checkDuplicateIsbn(bookDTO.getIsbn());
            book.setIsbn(bookDTO.getIsbn());
        }

        if (bookDTO.getAuthorId() != null) {
            var author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new InvalidAuthorException(HttpStatus.BAD_REQUEST.value()));
            book.setAuthor(author);
        }

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public Page<BookDTO> getAllBooks(Pageable page) {
        return bookRepository.findAll(page).map(bookMapper::toDTO);
    }

    public BookDTO getById(String id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new InvalidBookException(HttpStatus.NOT_FOUND.value()));
        return bookMapper.toDTO(book);
    }

    private void checkDuplicateIsbn(String isbn) {
        if(bookRepository.existsByIsbn(isbn)) {
            throw new IsbnConflictException(HttpStatus.CONFLICT.value());
        }
    }
}
