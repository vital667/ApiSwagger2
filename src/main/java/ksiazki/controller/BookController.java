package ksiazki.controller;

import io.swagger.annotations.ApiOperation;
import ksiazki.model.Book;
import ksiazki.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
        if (bookRepository.existsById(id))
            return new ResponseEntity<>(bookRepository.findById(id).get(), HttpStatus.OK);
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
    public ResponseEntity<Book> save(@RequestBody Book book) {
        boolean exists = ((List<Book>) bookRepository.findAll()).stream()
                .map(b->b.getTitle()).anyMatch(b->b.equals(book.getTitle()));
        if (!exists) return new ResponseEntity<>(
                bookRepository.save(book),
                HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "UPDATE with JSON")
    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if (bookRepository.existsById(book.getId())) {
            Book newBook = bookRepository.findById(book.getId()).get();
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
