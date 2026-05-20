package mate.academy.bookproject.repository;

import java.util.Optional;
import mate.academy.bookproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> existsByEmail(String email);
}
