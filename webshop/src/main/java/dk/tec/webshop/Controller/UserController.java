package dk.tec.webshop.Controller;

import dk.tec.webshop.Repo.UserRepository;
import dk.tec.webshop.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody User user) {
        try {
            if (repository.findByEmail(user.getEmail()) != null) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }
            user.setRole("USER");
            repository.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody User user) {
        User existingUser = repository.findByEmail(user.getEmail());

        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}


