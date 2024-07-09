package com.library.management.system.controller;

import com.library.management.system.dto.author.AuthorDTO;
import com.library.management.system.dto.author.AuthorWithBooksDTO;
import com.library.management.system.dto.author.UpdateAuthorDTO;
import com.library.management.system.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable String id) {
        return ResponseEntity.ok(authorService.update(id, authorDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDTO> partialUpdateAuthor(@RequestBody UpdateAuthorDTO updateAuthorDTO, @PathVariable String id) {
        return ResponseEntity.ok(authorService.patch(id, updateAuthorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AuthorWithBooksDTO>> getAllAuthors(Pageable page) {
        return ResponseEntity.ok(authorService.getAllAuthors(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorWithBooksDTO> getAuthorById(@PathVariable String id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }
}
