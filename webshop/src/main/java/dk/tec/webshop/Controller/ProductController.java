package dk.tec.webshop.Controller;

import dk.tec.webshop.Repo.ProductRepository;
import dk.tec.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

    ProductRepository repository;
    private final String uploadDir = "uploads/";

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Product product) {
        try {
            repository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
     Product read(@PathVariable int id) {
        return repository.findById(id).get();
    }
    @GetMapping()
    List<Product> getAll() {
        return repository.findAll();
    }

    @PutMapping()
    void update(@RequestBody Product product) {
        repository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        repository.deleteById(id);
    }
}
