package ksiazki.controller;


import io.swagger.annotations.ApiOperation;
import ksiazki.model.Book;
import ksiazki.model.Category;
import ksiazki.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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


//    @ApiOperation(value = "POST with JSON")
//    @PostMapping
//    public ResponseEntity<Category> save(@RequestBody Category category) {
//        boolean exists = ((List<Category>) categoryRepository.findAll()).stream()
//                .map(c -> c.getCategoryName())
//                .anyMatch(c -> c.equals(category.getCategoryName()));
//        if (!exists && category.getCategoryName()!=null) return new ResponseEntity<>(
//                categoryRepository.save(category),
//                HttpStatus.OK);
//        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }


//    @PostMapping
//    public Category save(@RequestBody Category category) {
//        System.out.println(category.getSecret());
//        System.out.println(category);
//        return categoryRepository.save(category);
//    }


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
        if (category.getCategoryId() != null && categoryRepository.existsById(category.getCategoryId())) {
            Category newCategory = categoryRepository.findById(category.getCategoryId()).get();
            newCategory.setCategoryId(category.getCategoryId());
            if (category.getCategoryName() != null)
                newCategory.setCategoryName(newCategory.getCategoryName());

            return new ResponseEntity<>(categoryRepository.save(newCategory), HttpStatus.OK);
        } else
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
