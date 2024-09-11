package dk.tec.webshop.Controller;

import dk.tec.webshop.Repo.UserRepository;
import dk.tec.webshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {
    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    void  create(@RequestBody User user) {
        repository.save(user);
    }

    @GetMapping("/{id}")
    User read(@PathVariable int id) {
        return repository.findById(id).get();
    }
    @GetMapping()
    List<User> getAll() {
        return repository.findAll();
    }

    @PutMapping()
    void update(@RequestBody User user) {
        repository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}
