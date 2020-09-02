package ksiazki.controller;

import io.swagger.annotations.ApiOperation;
import ksiazki.model.Book;
import ksiazki.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin   //api jest dostepne dla innych!!! white origin - z jakich serwerow moze korzystac api
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable int id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent())
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "COUNT number of users")
    @GetMapping("/count")
    public long count() {
        return bookRepository.count();
    }


    @GetMapping("/exists/{id}")
    public ResponseEntity<Book> ifExists(@PathVariable int id) {
        if (bookRepository.existsById(id))
            return new ResponseEntity<>(bookRepository.findById(id).get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "POST with JSON")
    @PostMapping
    public Book save(@RequestBody Book book) {
        return bookRepository.save(book);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(book.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "UPDATE with JSON and ID (parameter)")
    @PutMapping
    public ResponseEntity<Book> updateUser(
            @RequestBody Book book) {
        if (!bookRepository.findById(book.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        if (bookOptional.isPresent()) {
            Book newBook = bookOptional.get();
            newBook.setId(book.getId());
            if (book.getAuthor() != null)
                newBook.setAuthor(book.getAuthor());
            if (book.getTitle() != null)
                newBook.setTitle(book.getTitle());
            return new ResponseEntity<>(bookRepository.save(newBook), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "just CHECK connection and if working properly")
    @GetMapping("check")
    String check() {
        return "Check 1..2..3";
    }
}
