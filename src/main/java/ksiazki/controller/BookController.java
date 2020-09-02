package ksiazki.controller;

import io.swagger.annotations.ApiOperation;
import ksiazki.model.Book;
import ksiazki.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin   //api jest dostepne dla innych!!! white origin - z jakich serwerow moze korzystac api
@RestController
@RequestMapping("/api/")
public class BookController {

    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("books")
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }


    @GetMapping("books/{id}")
    public Optional<Book> findById(@PathVariable int id) {
        return bookRepository.findById(id);
    }


    @ApiOperation(value = "COUNT number of users")
    @GetMapping("books/count")
    public long count() {
        return bookRepository.count();
    }


    @GetMapping("books/exists/{id}")
    public String ifExists(@PathVariable int id) {
        return bookRepository.existsById(id) ? "User №" + id + " exists" : "User №" + id + " doesn't exist";
    }


    @ApiOperation(value = "POST with JSON")
    @PostMapping("books")
    public Book save(@RequestBody Book book) {
        return bookRepository.save(book);
    }


    @ApiOperation(value = "POST with Parameters")
    @PostMapping("books/save")
    public Book save(@RequestParam String title,
                     @RequestParam String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        return bookRepository.save(book);
    }


    @DeleteMapping("books/{id}")
    public String save(@PathVariable int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return "deleted";
        } else
            return "doesn't exists";
    }


    @ApiOperation(value = "UPDATE with JSON and ID (parameter)")
    @PutMapping("books/{id}")
    public String updateUser(
            @RequestBody Book book,
            @PathVariable int id
    ) {
        if (bookRepository.existsById(id)) {
            Book newBook = new Book();
            newBook.setId(id);
            newBook.setAuthor(book.getAuthor());
            newBook.setTitle(book.getTitle());
            bookRepository.save(newBook);
            System.out.println(newBook);
            System.out.println("new"+bookRepository.findById(id));
            return "User №" + id + " updated";
        } else
            return "doesn't exist";
    }


    @ApiOperation(value = "just CHECK connection and if working properly")
    @GetMapping("check") String check() {
        return "Check 1..2..3";
    }
}