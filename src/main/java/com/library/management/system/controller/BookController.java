package com.library.management.system.controller;

import com.library.management.system.dto.book.BookDTO;
import com.library.management.system.dto.book.UpdateBookDTO;
import com.library.management.system.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String id ,@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.update(id, bookDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> patchBook(@PathVariable String id ,@RequestBody UpdateBookDTO updateBookDTO) {
        return ResponseEntity.ok(bookService.patch(id, updateBookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(Pageable page) {
        return ResponseEntity.ok(bookService.getAllBooks(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getById(id));
    }
}