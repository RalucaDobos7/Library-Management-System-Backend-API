package com.library.management.system.service;

import com.library.management.system.dto.author.AuthorDTO;
import com.library.management.system.dto.author.AuthorWithBooksDTO;
import com.library.management.system.dto.author.UpdateAuthorDTO;
import com.library.management.system.entity.Author;
import com.library.management.system.exception.InvalidAuthorException;
import com.library.management.system.mapper.AuthorMapper;
import com.library.management.system.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorDTO save(AuthorDTO authorDTO) {
        var author = authorMapper.toEntity(authorDTO);
        authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    public AuthorDTO update(String id, AuthorDTO authorDTO) {
        var authorOptional = authorRepository.findById(id);

        Author author;
        if (authorOptional.isPresent()) {
            author = authorOptional.get();
            author.setBio(authorDTO.getBio());
            author.setName(authorDTO.getName());
        } else {
            author = authorMapper.toEntity(authorDTO);
            author.setId(id);
        }
        return authorMapper.toDTO(authorRepository.save(author));
    }

    public AuthorDTO patch(String id, UpdateAuthorDTO authorDTO) {
        var author = authorRepository.findById(id).orElseThrow(() -> new InvalidAuthorException(HttpStatus.NOT_FOUND.value()));

        if (authorDTO.getBio() != null) {
            author.setBio(authorDTO.getBio());
        }
        if (authorDTO.getName() != null) {
            author.setName(authorDTO.getName());
        }
        return authorMapper.toDTO(authorRepository.save(author));
    }

    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public Page<AuthorWithBooksDTO> getAllAuthors(Pageable page) {
        return authorRepository.findAll(page).map(authorMapper::toAuthorWIthBooksDTO);
    }

    @Transactional
    public AuthorWithBooksDTO getAuthorById(String id) {
        var author = authorRepository.findById(id).orElseThrow(() -> new InvalidAuthorException(HttpStatus.NOT_FOUND.value()));

        return authorMapper.toAuthorWIthBooksDTO(author);
    }
}
