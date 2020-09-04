package ksiazki.controller;

import io.swagger.annotations.ApiOperation;
import ksiazki.model.Book;
import ksiazki.model.Category;
import ksiazki.repository.BookRepository;
import ksiazki.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    private BookRepository bookRepository;

    public CategoryController(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable int id) {
        if (categoryRepository.existsById(id))
            return new ResponseEntity<>(categoryRepository.findById(id).get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "COUNT number of users")
    @GetMapping("/count")
    public long count() {
        return categoryRepository.count();
    }


    @GetMapping("/exists/{id}")
    public ResponseEntity<Category> ifExists(@PathVariable int id) {
        if (categoryRepository.existsById(id))
            return new ResponseEntity<>(categoryRepository.findById(id).get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "POST with JSON")
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Category category) {

        if (category.getCategoryId() != null )
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String title = category.getTitle();
        String author = category.getAuthor();

        if (title == null & author == null) {
            boolean exists = ((List<Category>) categoryRepository.findAll()).stream()
                    .map(c -> c.getCategoryName())
                    .anyMatch(c -> c.equals(category.getCategoryName()));
            if (!exists && category.getCategoryName() != null) return new ResponseEntity<>(
                    categoryRepository.save(category),
                    HttpStatus.OK);
        }

        if (title != null || author != null) {
            boolean exists = ((List<Category>) categoryRepository.findAll()).stream()
                    .map(c -> c.getCategoryName())
                    .anyMatch(c -> c.equals(category.getCategoryName()));
            if (exists) {
                Book book = new Book();
                book.setAuthor(author);
                book.setTitle(title);
                return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
            } else {
                List<Object> list = new ArrayList<>();
                Book book = new Book();
                book.setAuthor(author);
                book.setTitle(title);
                book.setCategoryId(categoryRepository.save(category).getCategoryId());
                list.add(bookRepository.save(book));
                list.add(categoryRepository.save(category));
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "UPDATE with JSON")
    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        Integer id = category.getCategoryId();
        String categoryName = category.getCategoryName();
        if (id != null && categoryRepository.existsById(id))
            if (categoryName != null) {
                Category newCategory = categoryRepository.findById(id).get();
                newCategory.setCategoryName(categoryName);
                return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> showBooks(@PathVariable int id) {
        if (categoryRepository.existsById(id))
            return new ResponseEntity<>(
                    categoryRepository.findById(id).get().getBooks(),
                    HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "just CHECK connection and if working properly")
    @GetMapping("check")
    String check() {
        return "Check 1..2..3";
    }
}