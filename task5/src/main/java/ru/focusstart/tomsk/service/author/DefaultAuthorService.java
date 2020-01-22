package ru.focusstart.tomsk.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.focusstart.tomsk.api.dto.AuthorDto;
import ru.focusstart.tomsk.api.dto.BookDto;
import ru.focusstart.tomsk.entity.Author;
import ru.focusstart.tomsk.exception.ObjectNotFoundException;
import ru.focusstart.tomsk.mapper.AuthorMapper;
import ru.focusstart.tomsk.mapper.BookMapper;
import ru.focusstart.tomsk.repository.author.AuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.focusstart.tomsk.service.validation.Validator.*;

@Service
@RequiredArgsConstructor
public class DefaultAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    private final BookMapper bookMapper;

    public DefaultAuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorDto authorDto) {
        validate(authorDto);

        Author author = add(null, authorDto);

        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getById(Long id) {
        checkNotNull("id", id);

        Author author = getAuthor(id);

        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> get(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name == null ? "" : name)
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBooks(Long id) {
        checkNotNull("id", id);

        return Optional.ofNullable(getAuthor(id).getBooks())
                .orElse(Collections.emptyList())
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto merge(Long id, AuthorDto authorDto) {
        checkNotNull("id", id);
        validate(authorDto);

        Author author = authorRepository.findById(id)
                .map(existing -> update(existing, authorDto))
                .orElseGet(() -> add(id, authorDto));

        return authorMapper.toDto(author);
    }

    private void validate(AuthorDto authorDto) {
        checkNull("author.id", authorDto.getId());
        checkSize("author.name", authorDto.getName(), 1, 256);
        checkSize("author.description", authorDto.getDescription(), 1, 4096);
    }

    private Author add(Long id, AuthorDto authorDto) {
        Author author = new Author();
        author.setId(id);
        author.setName(authorDto.getName());
        author.setDescription(authorDto.getDescription());

        return authorRepository.save(author);
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author with id %s not found", id)));
    }

    private Author update(Author author, AuthorDto authorDto) {
        author.setName(authorDto.getName());
        author.setDescription(authorDto.getDescription());

        return author;
    }
}
