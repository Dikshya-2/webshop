package dk.tec.webshop.Repo;

import dk.tec.webshop.model.Product;
import dk.tec.webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String username);

}
