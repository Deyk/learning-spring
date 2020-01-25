package ru.otus.spring.library.rest;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.rest.model.BookDto;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookDto> showBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        return allBooks.stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/books/add")
    public BookDto addBook(
            @RequestParam("title") String title,
            @RequestParam("authorName") String authorName) {
        Book book = bookService.addBook(title, authorName);
        return new BookDto(book);
    }

    @PostMapping("/books/edit")
    @JsonValue
    public BookDto editBook(@RequestBody BookDto request) throws LibraryServiceException {
        Book book = bookService.updateBook(request.getId(), request.getTitle(), request.getSelectedAuthor());
        return new BookDto(book);
    }

    @DeleteMapping("/books/delete")
    public ResponseEntity deleteBook(@RequestParam("id") String id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().body(true);
    }

    @ExceptionHandler(LibraryServiceException.class)
    public ResponseEntity<String> handleNotEnoughFunds(LibraryServiceException ex) {
        return ResponseEntity.badRequest().body("Not found book");
    }
}
