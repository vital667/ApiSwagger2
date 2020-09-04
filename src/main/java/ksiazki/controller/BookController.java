package ksiazki.controller;

import io.swagger.annotations.ApiOperation;
import ksiazki.model.Book;
import ksiazki.model.Category;
import ksiazki.repository.BookRepository;
import ksiazki.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
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
    public ResponseEntity<Object> save(@RequestBody Book book) {
        Integer categoryId = book.getCategoryId();
        String categoryName = book.getCategoryName();

        if (categoryId == null && categoryName == null)
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);

        if ((categoryId == null && categoryName != null) || (categoryId == 0 && categoryName != null)) {
            List<Object> list = new ArrayList<>();
            boolean existsCategory = ((List<Category>) categoryRepository.findAll()).stream()
                    .map(b -> b.getCategoryName()).anyMatch(b -> b.equals(book.getCategoryName()));
            if (!existsCategory) {
                Category category = new Category();
                category.setCategoryName(book.getCategoryName());
                book.setCategoryId(categoryRepository.save(category).getCategoryId());
                list.add(bookRepository.save(book));
                list.add(categoryRepository.save(category));
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                List<Category> list2 = (List<Category>) categoryRepository.findAll();
                for (Category i : list2)
                    if (i.getCategoryName().equals(categoryName)) book.setCategoryId(i.getCategoryId());
                return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
            }
        }

        if (categoryId == null && categoryName == null) {
            return new ResponseEntity<>(
                    bookRepository.save(book),
                    HttpStatus.OK);
        }

        if (categoryId == 0 && categoryName == null) {
            book.setCategoryId(null);
            return new ResponseEntity<>(
                    bookRepository.save(book),
                    HttpStatus.OK);
        }

        if (categoryId != null && categoryName != null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (categoryName == null) {
            if (!categoryRepository.existsById(categoryId))
                book.setCategoryId(null);
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            Book newBook = bookRepository.findById(book.getId()).get();
            newBook.setId(book.getId());
            if (book.getAuthor() != null)
                newBook.setAuthor(book.getAuthor());
            if (book.getTitle() != null)
                newBook.setTitle(book.getTitle());
            if (book.getCategoryId() != null)
                newBook.setCategoryId(book.getCategoryId());
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
