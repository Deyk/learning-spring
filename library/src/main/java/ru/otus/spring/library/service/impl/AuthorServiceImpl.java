package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JpaRepositoryException;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final MessageService ms;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao, MessageService ms) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.ms = ms;
    }

    @Override
    public Author addAuthor(String name) {
        Optional<Author> authorOptional = authorDao.getAuthorByName(name);
        return authorOptional.orElseGet(() -> authorDao.saveAuthor(new Author(0L, name)));
    }

    @Override
    public Author updateAuthor(long id, String name) throws LibraryServiceException {
        Author author;
        try {
            author = authorDao.getAuthorById(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get author with id: " + id);
        }
        author.setName(name);
        return authorDao.saveAuthor(author);
    }

    @Override
    public Author getAuthorById(long id) throws LibraryServiceException {
        try {
            return authorDao.getAuthorById(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get author with id: " + id);
        }
    }

    @Override
    public void deleteAuthorById(long id) throws LibraryServiceException {
        List<Book> allBooksWhereAuthorId = bookDao.getAllBooksWhereAuthorId(id);
        if (allBooksWhereAuthorId.size() > 0) {
            throw new LibraryServiceException("Can't delete author with id: " + id + ". Author still has books!");
        }
        try {
            authorDao.deleteAuthorById(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't delete author with id: " + id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }
}
