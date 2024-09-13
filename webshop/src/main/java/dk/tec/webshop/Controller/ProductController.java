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
import java.util.Optional;

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
        System.out.println("Product received: " + product.getName());  // Log product name
        try {
            repository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating product: " + e.getMessage());
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

    //    @PutMapping()
//    void update(@RequestBody Product product) {
//        repository.save(product);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable int id, @RequestBody Product newProductData) {
        Optional<Product> oldproductData = repository.findById(id);

        if (oldproductData.isPresent()) {
            Product updatedProductData = oldproductData.get();
            updatedProductData.setName(newProductData.getName());
            updatedProductData.setPrice(newProductData.getPrice());
            updatedProductData.setDescription(newProductData.getDescription());
            updatedProductData.setImageUrl(newProductData.getImageUrl());

            Product productObj = repository.save(updatedProductData);

            return new ResponseEntity<>(productObj, HttpStatus.OK);


        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        repository.deleteById(id);
    }
}
